package com.gmail.silina.katsiaryna.service.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DiscountStatusDTO {
    private Integer id;
    private String discountStatus;
    private BigDecimal discount;
}
