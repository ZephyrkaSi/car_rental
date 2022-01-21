package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.repository.model.DiscountStatus;
import com.gmail.silina.katsiaryna.repository.model.DiscountStatusEnum;

public interface DiscountStatusService {

    DiscountStatus getDiscountStatus(DiscountStatusEnum discountStatusEnum);
}
