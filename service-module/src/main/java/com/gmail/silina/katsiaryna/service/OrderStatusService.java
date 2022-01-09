package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.repository.model.OrderStatus;
import com.gmail.silina.katsiaryna.repository.model.OrderStatusEnum;

public interface OrderStatusService {
    OrderStatus getOrderStatus(OrderStatusEnum orderStatusEnum);
}
