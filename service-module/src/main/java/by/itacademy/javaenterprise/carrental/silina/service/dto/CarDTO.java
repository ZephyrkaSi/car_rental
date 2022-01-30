package by.itacademy.javaenterprise.carrental.silina.service.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
public class CarDTO {
    @NotNull
    private Long id;
    @NotNull
    private String stateNumber;
    @NotNull
    private CarModelDTO carModel;
    @NotNull
    private CarStatusDTO carStatus;
}
