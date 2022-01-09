package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.DiscountStatusRepository;
import com.gmail.silina.katsiaryna.repository.model.DiscountStatus;
import com.gmail.silina.katsiaryna.repository.model.DiscountStatusEnum;
import com.gmail.silina.katsiaryna.service.DiscountStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class DiscountStatusServiceImpl implements DiscountStatusService {
    private final DiscountStatusRepository discountStatusRepository;

    @Override
    public DiscountStatus getDiscountStatus(DiscountStatusEnum discountStatusEnum) {
        return discountStatusRepository.findByStatus(discountStatusEnum);
    }
}
