package by.itacademy.javaenterprise.carrental.silina.service.impl;

import by.itacademy.javaenterprise.carrental.silina.repository.OrderRepository;
import by.itacademy.javaenterprise.carrental.silina.repository.model.*;
import by.itacademy.javaenterprise.carrental.silina.service.*;
import by.itacademy.javaenterprise.carrental.silina.service.dto.OrderDTO;
import by.itacademy.javaenterprise.carrental.silina.service.dto.OrderFormDTO;
import by.itacademy.javaenterprise.carrental.silina.service.exception.OrderException;
import by.itacademy.javaenterprise.carrental.silina.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static by.itacademy.javaenterprise.carrental.silina.service.constant.ServiceConstant.MAX_DISCOUNT;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusService orderStatusService;
    private final UserService userService;
    private final CarService carService;
    private final ConvertService convertService;

    @Override
    public Order getOrderById(Long id) {
        if (id == null) {
            log.error("Order id cannot be null");
            throw new ServiceException("Order id cannot be null");
        } else {
            var orderOptional = orderRepository.findById(id);
            if (orderOptional.isPresent()) {
                return orderOptional.get();
            } else {
                log.error("Order with id {} doesn't exist", id);
                throw new ServiceException("Order with id " + id + " doesn't exist");
            }
        }
    }

    @Override
    public OrderDTO getOrderDTOById(Long id) {
        if (id == null) {
            log.error("Order id cannot be null");
            throw new ServiceException("Order id cannot be null");
        } else {
            var order = getOrderById(id);
            if (order == null) {
                log.error("Order with id {} doesn't exist", id);
                throw new ServiceException("Order with id " + id + " doesn't exist");
            } else {
                return convertService.getDTOFromObject(order, OrderDTO.class);
            }
        }

    }

    @Override
    public List<OrderDTO> getUserOrdersDTOsByUserId(Long userId) {
        if (userId == null) {
            log.error("User id cannot be null");
            throw new ServiceException("User id cannot be null");
        } else {
            var orders = orderRepository.getAllUserOrdersByUserId(userId);
            return convertService.getDTOsFromObjectList(orders, OrderDTO.class);
        }
    }

    @Override
    public List<OrderDTO> getAllOrderDTOs() {
        var orders = orderRepository.findAll();
        return convertService.getDTOsFromObjectList(orders, OrderDTO.class);
    }

    @Override
    public void addOrder(OrderFormDTO orderFormDTO) {
        var order = convertService.getObjectFromDTO(orderFormDTO, Order.class);

        var carModel = order.getCarModel();
        var carModelId = carModel.getId();
        var dateAndTimeFrom = order.getDateAndTimeFrom();
        var dateAndTimeTo = order.getDateAndTimeTo();

        order.setCar(getOrderCar(carModelId, dateAndTimeFrom, dateAndTimeTo));

        var userId = userService.getPrincipalUserId();
        var user = userService.getUserById(userId);
        order.setUser(user);

        order.setDiscount(getDiscount(user));

        var priceWithDiscount = getPriceWithDiscount(carModel, dateAndTimeFrom, dateAndTimeTo, user);
        order.setPriceWithDiscount(priceWithDiscount);

        var orderStatus = orderStatusService.getOrderStatus(OrderStatusEnum.WAITING_FOR_PAYMENT);
        order.setOrderStatus(orderStatus);
        log.info("Creating new order.");
        orderRepository.save(order);
    }

    @Override
    public void changeOrderStatusFromWaitingForPaymentToPaid(Long orderId) {
        if (orderId == null) {
            log.error("Order id cannot be null");
            throw new ServiceException("Order id cannot be null");
        } else {
            var order = getOrderById(orderId);
            if (order == null) {
                log.error("Order with id {} doesn't exist", orderId);
                throw new ServiceException("Order with id " + orderId + " doesn't exist");
            } else {
                var status = order.getOrderStatus();
                if (status.getOrderStatus().equals(OrderStatusEnum.WAITING_FOR_PAYMENT)) {
                    order.setOrderStatus(orderStatusService.getOrderStatus(OrderStatusEnum.PAID));
                    log.info("Changing order status with id {}", orderId);
                    orderRepository.save(order);
                }
            }
        }
    }

    @Override
    public void changeOrderStatusFromWaitingForPaymentToCanceledByClient(Long orderId) {
        var order = getOrderById(orderId);
        var status = order.getOrderStatus();
        if (status.getOrderStatus().equals(OrderStatusEnum.WAITING_FOR_PAYMENT)) {
            order.setOrderStatus(orderStatusService.getOrderStatus(OrderStatusEnum.CANCELED_BY_CLIENT));
            log.info("Changing order status with id {}", orderId);
            orderRepository.save(order);
        }
    }

    @Override
    @Transactional
    public void updateOrderStatusAndDeclineReasonFrom(OrderDTO orderDTO) {
        var orderId = orderDTO.getId();
        var order = getOrderById(orderId);

        var orderStatusId = orderDTO.getOrderStatus().getId();
        var orderStatus = orderStatusService.getOrderStatusById(orderStatusId);
        order.setOrderStatus(orderStatus);

        var declineReason = orderDTO.getDeclineReason();
        order.setDeclineReason(declineReason);
        log.info("Updating order status with id {} and decline reason", orderId);
        orderRepository.save(order);
    }

    private Car getOrderCar(Long carModelId, LocalDateTime dateAndTimeFrom, LocalDateTime dateAndTimeTo) {
        var availableCars = carService.getAvailableCars(carModelId, dateAndTimeFrom, dateAndTimeTo);
        if (availableCars.isEmpty()) {
            throw new OrderException("There is no available cars for this time range. Please change time range or pick another car model");
        }
        return availableCars.get(0);
    }

    private BigDecimal getPriceWithDiscount(CarModel carModel, LocalDateTime dateAndTimeFrom, LocalDateTime dateAndTimeTo, User user) {
        var orderMinutes = getOrderMinutes(dateAndTimeFrom, dateAndTimeTo);
        var totalPrice = getTotalPrice(carModel, orderMinutes);
        var userDiscount = getDiscount(user);
        return calculatePriceWithDiscount(totalPrice, userDiscount);
    }

    private long getOrderMinutes(LocalDateTime dateAndTimeFrom, LocalDateTime dateAndTimeTo) {
        var duration = Duration.between(dateAndTimeFrom, dateAndTimeTo);
        return Math.abs(duration.toMinutes());
    }

    private BigDecimal getTotalPrice(CarModel carModel, long minutes) {
        var pricePerMinute = carModel.getPricePerMinute();
        return BigDecimal.valueOf(pricePerMinute * minutes);
    }

    private BigDecimal getDiscount(User user) {
        return user.getUserDetails().getDiscountStatus().getDiscount();
    }

    private BigDecimal calculatePriceWithDiscount(BigDecimal totalPrice, BigDecimal userDiscount) {
        BigDecimal maxDiscount = BigDecimal.valueOf(MAX_DISCOUNT);
        return maxDiscount.subtract(userDiscount).divide(maxDiscount).multiply(totalPrice);
    }
}
