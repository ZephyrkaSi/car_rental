package by.itacademy.javaenterprise.carrental.silina.service.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class RepairInvoiceDTO {
    private Long id;
    private LocalDateTime date = LocalDateTime.now();
    private UserDTO client;
    private CarDTO car;
    private String damageInfo;
    private BigDecimal cost;
    private UserDTO admin;
    private String addInfo;
    private boolean isPaid;
}
