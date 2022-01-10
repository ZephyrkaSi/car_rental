package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.service.dto.OrderDTO;
import com.gmail.silina.katsiaryna.service.dto.OrderFormDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> getAllOrders();

    void addOrder(OrderFormDTO orderFormDTO);
}
