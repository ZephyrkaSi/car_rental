package com.gmail.silina.katsiaryna.service.dto;

import lombok.Data;

@Data
public class CarDTO {
    private Long id;
    private String stateNumber;
    private CarModelDTO carModel;
    private CarStatusDTO carStatus;
}
