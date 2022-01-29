package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.config.AppConfig;
import com.gmail.silina.katsiaryna.repository.CarModelRepository;
import com.gmail.silina.katsiaryna.repository.OrderRepository;
import com.gmail.silina.katsiaryna.repository.UserRepository;
import com.gmail.silina.katsiaryna.repository.model.OrderStatusEnum;
import com.gmail.silina.katsiaryna.repository.model.User;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.OrderService;
import com.gmail.silina.katsiaryna.service.UserService;
import com.gmail.silina.katsiaryna.service.dto.OrderDTO;
import com.gmail.silina.katsiaryna.service.dto.OrderFormDTO;
import com.gmail.silina.katsiaryna.service.dto.OrderStatusDTO;
import com.gmail.silina.katsiaryna.service.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@DataJpaTest
@Sql(scripts = {"classpath:/schema.sql", "/data.sql"})
@ContextConfiguration(classes = {AppConfig.class, IntegrationConfig.class})
@EnableJpaRepositories(basePackages = {"com.gmail.silina.katsiaryna.repository"})
@EntityScan("com.gmail.silina.katsiaryna.repository.model")
@TestPropertySource(locations = {"classpath:application.properties"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderServiceImplTest {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CarModelRepository carModelRepository;
    @Autowired
    private ConvertService convertService;

    @MockBean
    private UserService userService;

    static Stream<Arguments> createOrderIdVariety() {
        return Stream.of(
                //Long orderId, boolean expectOrderInDB
                arguments(1L, true),
                arguments(20L, false),
                arguments(null, false)
        );
    }

    static Stream<Long> createUserIdVariety() {
        return Stream.of(
                1L,
                null
        );
    }

    @ParameterizedTest
    @MethodSource("createOrderIdVariety")
    void getOrderById(Long orderId, boolean expectOrderInDB) {
        if (expectOrderInDB) {
            var actualOrder = orderService.getOrderById(orderId);
            Assertions.assertNotNull(actualOrder);

            var expectedOrder = orderRepository.findById(orderId).get();
            Assertions.assertEquals(expectedOrder, actualOrder);
        } else {
            var serviceException = Assertions.assertThrows(Exception.class, () -> {
                orderService.getOrderById(orderId);
            });

            if (orderId == null) {
                Assertions.assertEquals("Order id cannot be null", serviceException.getMessage());
            } else {
                Assertions.assertEquals("Order with id " + orderId + " doesn't exist", serviceException.getMessage());
            }
        }
    }

    @ParameterizedTest
    @MethodSource("createOrderIdVariety")
    void getOrderDTOById(Long orderId, boolean expectOrderInDB) {
        if (expectOrderInDB) {
            var orderDTO = orderService.getOrderDTOById(orderId);
            Assertions.assertNotNull(orderDTO);
            Assertions.assertEquals(OrderDTO.class, orderDTO.getClass());

            //checking fields
            var actualOrder = orderService.getOrderById(orderId);
            Assertions.assertEquals(actualOrder.getId(), orderDTO.getId());
            Assertions.assertEquals(actualOrder.getUser().getId(), orderDTO.getUser().getId());
            Assertions.assertEquals(actualOrder.getDateAndTimeFrom(), orderDTO.getDateAndTimeFrom());
            Assertions.assertEquals(actualOrder.getDateAndTimeTo(), orderDTO.getDateAndTimeTo());
            Assertions.assertEquals(actualOrder.getCarModel().getId(), orderDTO.getCarModel().getId());
            Assertions.assertEquals(actualOrder.getCar().getId(), orderDTO.getCar().getId());
            Assertions.assertEquals(actualOrder.getPriceWithDiscount(), orderDTO.getPriceWithDiscount());
            Assertions.assertEquals(actualOrder.getDiscount(), orderDTO.getDiscount());
            Assertions.assertEquals(actualOrder.getDeclineReason(), orderDTO.getDeclineReason());
            Assertions.assertEquals(actualOrder.getOrderStatus().getId(), orderDTO.getOrderStatus().getId());
        } else {
            var serviceException = Assertions.assertThrows(ServiceException.class, () -> {
                orderService.getOrderDTOById(orderId);
            });

            if (orderId == null) {
                Assertions.assertEquals("Order id cannot be null", serviceException.getMessage());
            } else {
                Assertions.assertEquals("Order with id " + orderId + " doesn't exist", serviceException.getMessage());
            }
        }
    }

    @ParameterizedTest
    @MethodSource("createUserIdVariety")
    void getUserOrdersDTOsByUserId(Long userId) {
        if (userId == null) {
            var serviceException = Assertions.assertThrows(ServiceException.class, () -> {
                orderService.getUserOrdersDTOsByUserId(userId);
            });
            Assertions.assertEquals("User id cannot be null", serviceException.getMessage());
        } else {
            var orderDTOs = orderService.getUserOrdersDTOsByUserId(userId);
            Assertions.assertEquals(1, orderDTOs.size());
        }
    }

    @Test
    void getAllOrderDTOs() {
        var orders = orderRepository.findAll();
        var expectedOrderDTOs = convertService.getDTOsFromObjectList(orders, OrderDTO.class);
        var resultOrderDTOs = orderService.getAllOrderDTOs();
        Assertions.assertEquals(expectedOrderDTOs, resultOrderDTOs);
    }

    @Test
    void addOrder() {
        OrderFormDTO orderFormDTO = new OrderFormDTO();
        //todo maggic numbers . NOT ONLY IN THIS TEST
        var carModel = carModelRepository.findById(4L).get();
        orderFormDTO.setCarModel(carModel);
        orderFormDTO.setDateAndTimeFrom(LocalDateTime.of(2017, Month.JULY, 9, 11, 10, 0));
        orderFormDTO.setDateAndTimeTo(LocalDateTime.of(2017, Month.JULY, 9, 17, 10, 0));

        Mockito.when(userService.getPrincipalUserId()).thenReturn(1L);
        User user = userRepository.findById(1L).get();
        Mockito.when(userService.getUserById(1L)).thenReturn(user);

        int ordersNumberBefore = orderRepository.findAll().size();
        orderService.addOrder(orderFormDTO);
        int ordersNumberAfter = orderRepository.findAll().size();
        Assertions.assertEquals(ordersNumberBefore, ordersNumberAfter - 1);
    }

    @ParameterizedTest
    @MethodSource("createOrderIdVariety")
    void changeOrderStatusFromWaitingForPaymentToPaid(Long orderId, boolean expectOrderInDB) {
        if (expectOrderInDB) {
            var order = orderRepository.findById(orderId).get();
            var actualOrderStatusEnum = order.getOrderStatus().getOrderStatus();
            Assertions.assertEquals(OrderStatusEnum.WAITING_FOR_PAYMENT, actualOrderStatusEnum);

            orderService.changeOrderStatusFromWaitingForPaymentToPaid(orderId);

            var changedOrderStatusEnum = order.getOrderStatus().getOrderStatus();
            Assertions.assertEquals(OrderStatusEnum.PAID, changedOrderStatusEnum);
        } else {
            var serviceException = Assertions.assertThrows(Exception.class, () -> {
                orderService.getOrderById(orderId);
            });

            if (orderId == null) {
                Assertions.assertEquals("Order id cannot be null", serviceException.getMessage());
            } else {
                Assertions.assertEquals("Order with id " + orderId + " doesn't exist", serviceException.getMessage());
            }
        }
    }

    @ParameterizedTest
    @MethodSource("createOrderIdVariety")
    void changeOrderStatusFromWaitingForPaymentToCanceledByClient(Long orderId, boolean expectOrderInDB) {
        if (expectOrderInDB) {
            var order = orderRepository.findById(orderId).get();
            var actualOrderStatusEnum = order.getOrderStatus().getOrderStatus();
            Assertions.assertEquals(OrderStatusEnum.WAITING_FOR_PAYMENT, actualOrderStatusEnum);

            orderService.changeOrderStatusFromWaitingForPaymentToCanceledByClient(orderId);

            var changedOrderStatusEnum = order.getOrderStatus().getOrderStatus();
            Assertions.assertEquals(OrderStatusEnum.CANCELED_BY_CLIENT, changedOrderStatusEnum);
        } else {
            var serviceException = Assertions.assertThrows(Exception.class, () -> {
                orderService.getOrderById(orderId);
            });

            if (orderId == null) {
                Assertions.assertEquals("Order id cannot be null", serviceException.getMessage());
            } else {
                Assertions.assertEquals("Order with id " + orderId + " doesn't exist", serviceException.getMessage());
            }
        }
    }

    @Test
    void updateOrderStatusAndDeclineReasonFrom() {
        OrderDTO orderDTO = new OrderDTO();
        OrderStatusDTO orderStatusDTO = new OrderStatusDTO();
        orderStatusDTO.setId(5L);
        orderStatusDTO.setDescription(OrderStatusEnum.WAITING_FOR_PAYMENT.name());
        orderStatusDTO.setOrderStatus(OrderStatusEnum.WAITING_FOR_PAYMENT.getStatus());
        orderDTO.setOrderStatus(orderStatusDTO);
        Long orderId = 1L;
        orderDTO.setId(orderId);
        orderDTO.setDeclineReason("Some reason");

        var expectedOrderStatusId = orderDTO.getOrderStatus().getId();
        var expectedDeclineReason = orderDTO.getDeclineReason();

        var order = orderRepository.findById(orderId).get();
        var orderStatusId = order.getOrderStatus().getId();
        var declineReason = order.getDeclineReason();
        Assertions.assertNotEquals(expectedOrderStatusId, orderStatusId);
        Assertions.assertNotEquals(expectedDeclineReason, declineReason);

        orderService.updateOrderStatusAndDeclineReasonFrom(orderDTO);

        var orderAfterUpdate = orderRepository.findById(orderId).get();
        var orderStatusIdAfterUpdate = orderAfterUpdate.getOrderStatus().getId();
        var declineReasonAfterUpdate = orderAfterUpdate.getDeclineReason();
        Assertions.assertEquals(expectedOrderStatusId, orderStatusIdAfterUpdate);
        Assertions.assertEquals(expectedDeclineReason, declineReasonAfterUpdate);
    }
}