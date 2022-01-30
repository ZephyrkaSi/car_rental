package by.itacademy.javaenterprise.carrental.silina.service;

import by.itacademy.javaenterprise.carrental.silina.repository.model.DiscountStatus;
import by.itacademy.javaenterprise.carrental.silina.repository.model.DiscountStatusEnum;

public interface DiscountStatusService {

    DiscountStatus getDiscountStatus(DiscountStatusEnum discountStatusEnum);
}
