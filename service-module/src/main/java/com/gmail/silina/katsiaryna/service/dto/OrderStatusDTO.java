package com.gmail.silina.katsiaryna.service.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderStatusDTO {
    private Long id;
    private String orderStatus;
    private String description;
}
