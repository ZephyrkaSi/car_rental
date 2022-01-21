package com.gmail.silina.katsiaryna.repository.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@AllArgsConstructor
@Getter
public enum OrderStatusEnum {
    WAITING_FOR_PAYMENT("WAITING FOR PAYMENT"),
    PAID("PAID"),
    CONFIRMED("CONFIRMED"),
    COMPLETED("COMPLETED"),
    DECLINED_BY_ADMIN("DECLINED BY ADMIN"),
    CANCELED_BY_CLIENT("CANCELED BY CLIENT");

    private final String status;

    public static OrderStatusEnum findByStatus(String status) {
        return Arrays.stream(OrderStatusEnum.values())
                .filter(el -> el.getStatus().equals(status))
                .findFirst().orElse(null);
    }
}