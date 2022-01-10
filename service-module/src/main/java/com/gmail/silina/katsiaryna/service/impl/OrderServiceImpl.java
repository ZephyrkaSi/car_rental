package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.CarModelRepository;
import com.gmail.silina.katsiaryna.repository.OrderRepository;
import com.gmail.silina.katsiaryna.repository.UserRepository;
import com.gmail.silina.katsiaryna.repository.model.*;
import com.gmail.silina.katsiaryna.service.CarService;
import com.gmail.silina.katsiaryna.service.OrderService;
import com.gmail.silina.katsiaryna.service.OrderStatusService;
import com.gmail.silina.katsiaryna.service.dto.OrderDTO;
import com.gmail.silina.katsiaryna.service.dto.OrderFormDTO;
import com.gmail.silina.katsiaryna.service.exception.OrderException;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.silina.katsiaryna.service.constant.ServiceConstant.MAX_DISCOUNT;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderStatusService orderStatusService;
    //TODO REMOVE THIS WHEN LOGIN ADDED
    private final UserRepository userRepository;
    //TODO REMOVE THIS WHEN CAR MODEL FORM ADDED
    private final CarModelRepository carModelRepository;
    private final CarService carService;
    private final ModelMapper modelMapper;

    @Override
    public List<OrderDTO> getAllOrders() {
        var orders = orderRepository.findAll();
        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());
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

        //TODO REMOVE THIS WHEN LOGIN ADDED
        var user = userRepository.findById(3L).get();
        order.setUser(user);

        order.setDiscount(getDiscount(user));

        var priceWithDiscount = getPriceWithDiscount(carModel, dateAndTimeFrom, dateAndTimeTo, user);
        order.setPriceWithDiscount(priceWithDiscount);

        order.setOrderStatus(orderStatusService.getOrderStatus(OrderStatusEnum.WAITING_FOR_PAYMENT));
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
