package by.itacademy.javaenterprise.carrental.silina.service;

import by.itacademy.javaenterprise.carrental.silina.repository.model.OrderStatus;
import by.itacademy.javaenterprise.carrental.silina.repository.model.OrderStatusEnum;
import by.itacademy.javaenterprise.carrental.silina.service.dto.OrderDTO;
import by.itacademy.javaenterprise.carrental.silina.service.dto.OrderStatusDTO;

import java.util.List;

public interface OrderStatusService {

    OrderStatus getOrderStatusById(Long orderStatusId);

    OrderStatus getOrderStatus(OrderStatusEnum orderStatusEnum);

    List<OrderStatus> getAllStatuses();

    List<OrderStatusDTO> getEligibleStatusesForOrder(OrderDTO orderDTO);
}
