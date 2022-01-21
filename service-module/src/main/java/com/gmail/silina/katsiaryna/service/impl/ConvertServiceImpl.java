package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.model.Order;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.dto.OrderDTO;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ConvertServiceImpl implements ConvertService {
    private final ModelMapper modelMapper;

   /* @Override
    public OrderDTO getDTOFromObject(Order order) {
        return modelMapper.map(order, OrderDTO.class);
    }

    @Override
    public Order getObjectFromDTO(OrderDTO orderDTO) {
        return modelMapper.map(orderDTO, Order.class);
    }*/

    @Override
    public <T, D> T getObjectFromDTO(D dto, Class<T> clazz) {
        return modelMapper.map(dto, clazz);
    }

    @Override
    public <T, D> D getDTOFromObject(T obj, Class<D> dto) {
        return modelMapper.map(obj, dto);
    }

    @Override
    public <T, D> List<D> getDTOsFromObjectList(List<T> list, Class<D> clazz) {
        return list.stream()
                .map(el -> modelMapper.map(el, clazz))
                .collect(Collectors.toList());
/*        return orders.stream()
                .map(order -> modelMapper.map(order, OrderDTO.class))
                .collect(Collectors.toList());*/
    }

    @Override
    public List<Order> getObjectListFromDTOList(List<OrderDTO> orderDTOs) {
        return orderDTOs.stream()
                .map(order -> modelMapper.map(order, Order.class))
                .collect(Collectors.toList());
    }

/*    @Override
    public List<OrderStatusDTO> getDTOsFromObjectList(List<OrderStatus> orderStatuses) {
        return orderStatuses.stream()
                .map(orderStatus -> modelMapper.map(orderStatus, OrderStatusDTO.class))
                .collect(Collectors.toList());
    }*/
}
