package by.itacademy.javaenterprise.carrental.silina.service.dto;

import lombok.Data;

@Data
public class UserDetailsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String passportData;
    private Integer totalRentalTime = 0;
    private DiscountStatusDTO discountStatus;
}
