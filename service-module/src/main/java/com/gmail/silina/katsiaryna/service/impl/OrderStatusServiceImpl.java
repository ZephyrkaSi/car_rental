package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.OrderStatusRepository;
import com.gmail.silina.katsiaryna.repository.model.OrderStatus;
import com.gmail.silina.katsiaryna.repository.model.OrderStatusEnum;
import com.gmail.silina.katsiaryna.service.OrderStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;

    @Override
    public OrderStatus getOrderStatus(OrderStatusEnum orderStatusEnum) {
        return orderStatusRepository.findByStatus(orderStatusEnum);
    }
}
