package by.itacademy.javaenterprise.carrental.silina.service.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class OrderDTO {
    private Long id;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime orderDate = LocalDateTime.now();
    private UserDTO user;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateAndTimeFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateAndTimeTo;
    private CarModelDTO carModel;
    private CarDTO car;
    private BigDecimal priceWithDiscount;
    private BigDecimal discount;
    private String declineReason;
    private OrderStatusDTO orderStatus;
}
