package com.gmail.silina.katsiaryna.service.model;

import lombok.Data;

@Data
public class UserDetailsDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String passportData;
    private Integer totalRentalTime = 0;
    private Integer statusId = 1;
}
