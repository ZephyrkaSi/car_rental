package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.OrderRepository;
import com.gmail.silina.katsiaryna.repository.model.*;
import com.gmail.silina.katsiaryna.service.*;
import com.gmail.silina.katsiaryna.service.dto.OrderDTO;
import com.gmail.silina.katsiaryna.service.dto.OrderFormDTO;
import com.gmail.silina.katsiaryna.service.exception.OrderException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import static com.gmail.silina.katsiaryna.service.constant.ServiceConstant.MAX_DISCOUNT;

@Slf4j
@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusService orderStatusService;
    private final UserService userService;
    private final CarService carService;
    private final ModelMapper modelMapper;
    private final ConvertService convertService;

    @Override
    public Order getOrderById(Long id) {
        var optionalOrder = orderRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    @Override
    public OrderDTO getOrderDTOById(Long id) {
        var order = getOrderById(id);
        return convertService.getDTOFromObject(order, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getUserOrdersDTOsByUserId(Long userId) {
        var orders = orderRepository.getAllUserOrdersByUserId(userId);
        return convertService.getDTOsFromObjectList(orders, OrderDTO.class);
    }

    @Override
    public List<OrderDTO> getAllOrderDTOs() {
        var orders = orderRepository.findAll();
        return convertService.getDTOsFromObjectList(orders, OrderDTO.class);
    }

    @Override
    public void addOrder(OrderFormDTO orderFormDTO) {
        //TODO ADD LOGS
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        var order = modelMapper.map(orderFormDTO, Order.class);

        var carModel = order.getCarModel();
        var dateAndTimeFrom = order.getDateAndTimeFrom();
        var dateAndTimeTo = order.getDateAndTimeTo();

        order.setCar(getOrderCar(carModel, dateAndTimeFrom, dateAndTimeTo));

        var userId = userService.getPrincipalUserId();
        var user = userService.getUserById(userId);
        order.setUser(user);

        order.setDiscount(getDiscount(user));

        var priceWithDiscount = getPriceWithDiscount(carModel, dateAndTimeFrom, dateAndTimeTo, user);
        order.setPriceWithDiscount(priceWithDiscount);

        var orderStatus = orderStatusService.getOrderStatus(OrderStatusEnum.WAITING_FOR_PAYMENT);
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
    }

    @Override
    public void changeOrderStatusFromWaitingForPaymentToPaid(Long orderId) {
        //TODO log
        var order = getOrderById(orderId);
        var status = order.getOrderStatus();
        if (status.getOrderStatusEnum().equals(OrderStatusEnum.WAITING_FOR_PAYMENT)) {
            order.setOrderStatus(orderStatusService.getOrderStatus(OrderStatusEnum.PAID));
            orderRepository.save(order);
            log.info("");
        } else {
            log.info("");
        }
    }

    @Override
    public void changeOrderStatusFromWaitingForPaymentToCanceledByClient(Long orderId) {
        //TODO log
        var order = getOrderById(orderId);
        var status = order.getOrderStatus();
        if (status.getOrderStatusEnum().equals(OrderStatusEnum.WAITING_FOR_PAYMENT)) {
            order.setOrderStatus(orderStatusService.getOrderStatus(OrderStatusEnum.CANCELED_BY_CLIENT));
            orderRepository.save(order);
            log.info("");
        } else {
            log.info("");
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
        orderRepository.save(order);
    }

    private Car getOrderCar(CarModel carModel, LocalDateTime dateAndTimeFrom, LocalDateTime dateAndTimeTo) {
        var availableCars = carService.getAvailableCars(carModel, dateAndTimeFrom, dateAndTimeTo);
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
