package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.repository.model.OrderStatus;
import com.gmail.silina.katsiaryna.repository.model.OrderStatusEnum;
import com.gmail.silina.katsiaryna.service.dto.OrderDTO;
import com.gmail.silina.katsiaryna.service.dto.OrderStatusDTO;

import java.util.List;

public interface OrderStatusService {

    OrderStatus getOrderStatusById(Long orderStatusId);
    
    OrderStatus getOrderStatus(OrderStatusEnum orderStatusEnum);

    List<OrderStatus> getAllStatuses();

    List<OrderStatusDTO> getEligibleStatusesForOrder(OrderDTO orderDTO);
}
