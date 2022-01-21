package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.repository.model.Order;
import com.gmail.silina.katsiaryna.service.dto.OrderDTO;

import java.util.List;

public interface ConvertService {

    <T, D> T getObjectFromDTO(D dto, Class<T> clazz);

    <T, D> D getDTOFromObject(T obj, Class<D> dto);

  /*  OrderDTO getDTOFromObject(Order order);

    Order getObjectFromDTO(OrderDTO orderDTO);*/

    <T, D> List<D> getDTOsFromObjectList(List<T> list, Class<D> clazz);

    List<Order> getObjectListFromDTOList(List<OrderDTO> orderDTOs);

/*
    List<OrderStatusDTO> getDTOsFromObjectList(List<OrderStatus> orderStatuses);
*/

}
