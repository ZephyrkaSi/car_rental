package by.itacademy.javaenterprise.carrental.silina.service.dto;

import lombok.Data;

@Data
public class CarModelDTO {
    private Long id;
    private String brandName;
    private String model;
    private String bodyType;
    private String fuelType;
    private Double engineVolume;
    private String transmission;
    private String bodyColor;
    private String interiorColor;
    private Integer pricePerMinute;
}
