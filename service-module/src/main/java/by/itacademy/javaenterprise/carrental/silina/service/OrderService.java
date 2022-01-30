package by.itacademy.javaenterprise.carrental.silina.service;

import by.itacademy.javaenterprise.carrental.silina.repository.model.Order;
import by.itacademy.javaenterprise.carrental.silina.service.dto.OrderDTO;
import by.itacademy.javaenterprise.carrental.silina.service.dto.OrderFormDTO;

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
