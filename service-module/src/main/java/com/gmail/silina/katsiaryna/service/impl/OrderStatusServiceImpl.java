package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.OrderStatusRepository;
import com.gmail.silina.katsiaryna.repository.model.OrderStatus;
import com.gmail.silina.katsiaryna.repository.model.OrderStatusEnum;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.OrderStatusService;
import com.gmail.silina.katsiaryna.service.dto.OrderDTO;
import com.gmail.silina.katsiaryna.service.dto.OrderStatusDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.gmail.silina.katsiaryna.repository.model.OrderStatusEnum.CONFIRMED;

@Service
@AllArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;
    private final ConvertService convertService;

    @Override
    public OrderStatus getOrderStatusById(Long orderStatusId) {
        var optionalOrderStatus = orderStatusRepository.findById(orderStatusId);
        return optionalOrderStatus.orElse(null);
    }

    @Override
    public OrderStatus getOrderStatus(OrderStatusEnum orderStatusEnum) {
        return orderStatusRepository.findByStatus(orderStatusEnum);
    }

    @Override
    public List<OrderStatus> getAllStatuses() {
        return orderStatusRepository.findAll();
    }

    @Override
    public List<OrderStatusDTO> getEligibleStatusesForOrder(OrderDTO orderDTO) {
        var orderStatusDTO = orderDTO.getOrderStatus();
        var status = orderStatusDTO.getOrderStatus();
        var statusEnum = OrderStatusEnum.findByStatus(status);

        var statuses = convertService.getDTOsFromObjectList(getAllStatuses(), OrderStatusDTO.class);

        switch (statusEnum) {
            case WAITING_FOR_PAYMENT:
            case CONFIRMED:
                return statuses.stream()
                        .filter(el -> el.getOrderStatus().equals(OrderStatusEnum.DECLINED_BY_ADMIN.getStatus()))
                        .collect(Collectors.toList());
            case PAID:
                return statuses.stream()
                        .filter(el -> el.getOrderStatus().equals(CONFIRMED.getStatus()) ||
                                el.getOrderStatus().equals(OrderStatusEnum.DECLINED_BY_ADMIN.getStatus()))
                        .collect(Collectors.toList());
            default:
                return List.of(orderStatusDTO);
        }
    }
}
