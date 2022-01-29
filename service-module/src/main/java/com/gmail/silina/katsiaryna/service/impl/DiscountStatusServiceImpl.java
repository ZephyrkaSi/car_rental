package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.DiscountStatusRepository;
import com.gmail.silina.katsiaryna.repository.model.DiscountStatus;
import com.gmail.silina.katsiaryna.repository.model.DiscountStatusEnum;
import com.gmail.silina.katsiaryna.service.DiscountStatusService;
import com.gmail.silina.katsiaryna.service.exception.ServiceException;
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
