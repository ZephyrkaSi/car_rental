package by.itacademy.javaenterprise.carrental.silina.service.impl;

import by.itacademy.javaenterprise.carrental.silina.repository.OrderStatusRepository;
import by.itacademy.javaenterprise.carrental.silina.repository.model.OrderStatus;
import by.itacademy.javaenterprise.carrental.silina.repository.model.OrderStatusEnum;
import by.itacademy.javaenterprise.carrental.silina.service.ConvertService;
import by.itacademy.javaenterprise.carrental.silina.service.OrderStatusService;
import by.itacademy.javaenterprise.carrental.silina.service.dto.OrderDTO;
import by.itacademy.javaenterprise.carrental.silina.service.dto.OrderStatusDTO;
import by.itacademy.javaenterprise.carrental.silina.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static by.itacademy.javaenterprise.carrental.silina.repository.model.OrderStatusEnum.CONFIRMED;

@Slf4j
@Service
@AllArgsConstructor
public class OrderStatusServiceImpl implements OrderStatusService {
    private final OrderStatusRepository orderStatusRepository;
    private final ConvertService convertService;

    @Override
    public OrderStatus getOrderStatusById(Long orderStatusId) {
        if (orderStatusId == null) {
            log.error("Order status id cannot be null");
            throw new ServiceException("Order status id cannot be null");
        } else {
            var optionalOrderStatus = orderStatusRepository.findById(orderStatusId);
            if (optionalOrderStatus.isPresent()) {
                return optionalOrderStatus.get();
            } else {
                log.error("Order status with id {} doesn't exist", orderStatusId);
                throw new ServiceException("Order status with id " + orderStatusId + " doesn't exist");
            }
        }
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
        var statusEnum = OrderStatusEnum.findByEnumName(status);

        var statuses = convertService.getDTOsFromObjectList(getAllStatuses(), OrderStatusDTO.class);

        switch (statusEnum) {
            case WAITING_FOR_PAYMENT:
            case CONFIRMED:
                return statuses.stream()
                        .filter(el -> el.getId().equals(5L))
                        .collect(Collectors.toList());
            case PAID:
                return statuses.stream()
                        .filter(el -> el.getOrderStatus().equals(CONFIRMED.getStatus()) ||
                                el.getId().equals(5L))
                        .collect(Collectors.toList());
            default:
                return List.of(orderStatusDTO);
        }
    }
}
