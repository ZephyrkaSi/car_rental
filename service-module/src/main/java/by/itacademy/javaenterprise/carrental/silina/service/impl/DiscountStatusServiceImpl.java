package by.itacademy.javaenterprise.carrental.silina.service.impl;

import by.itacademy.javaenterprise.carrental.silina.repository.DiscountStatusRepository;
import by.itacademy.javaenterprise.carrental.silina.repository.model.DiscountStatus;
import by.itacademy.javaenterprise.carrental.silina.repository.model.DiscountStatusEnum;
import by.itacademy.javaenterprise.carrental.silina.service.DiscountStatusService;
import by.itacademy.javaenterprise.carrental.silina.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class DiscountStatusServiceImpl implements DiscountStatusService {
    private final DiscountStatusRepository discountStatusRepository;

    @Override
    public DiscountStatus getDiscountStatus(DiscountStatusEnum discountStatusEnum) {
        if (discountStatusEnum == null) {
            log.error("DiscountStatusEnum cannot be null");
            throw new ServiceException("DiscountStatusEnum cannot be null");
        } else {
            return discountStatusRepository.findByStatus(discountStatusEnum);
        }
    }
}
