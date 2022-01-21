package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.repository.model.Order;
import com.gmail.silina.katsiaryna.service.dto.OrderDTO;
import com.gmail.silina.katsiaryna.service.dto.OrderFormDTO;

import java.util.List;

public interface OrderService {

    Order getOrderById(Long id);

    OrderDTO getOrderDTOById(Long id);

    List<OrderDTO> getUserOrdersDTOsByUserId(Long userId);

    List<OrderDTO> getAllOrderDTOs();

    void addOrder(OrderFormDTO orderFormDTO);

    void changeOrderStatusFromWaitingForPaymentToPaid(Long orderId);

    void changeOrderStatusFromWaitingForPaymentToCanceledByClient(Long orderId);

    void updateOrderStatusAndDeclineReasonFrom(OrderDTO orderDTO);
}
