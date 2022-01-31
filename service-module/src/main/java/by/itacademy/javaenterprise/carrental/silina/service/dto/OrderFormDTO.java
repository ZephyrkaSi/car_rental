package by.itacademy.javaenterprise.carrental.silina.service.dto;

import by.itacademy.javaenterprise.carrental.silina.repository.model.CarModel;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDateTime;

@Data
@Validated
public class OrderFormDTO {
    @NotNull(message = "Date and time should be valid")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateAndTimeFrom;
    @NotNull(message = "Date and time should be valid")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateAndTimeTo;
    @NotNull
    private CarModel carModel;

    @AssertTrue(message = "Picked incorrect dates and time.\n" +
            "Order date and time from must be after then order date and time to.\n" +
            "And order date and time from must be after current date and time.")
    public boolean isValid() {
        return dateAndTimeFrom.isBefore(dateAndTimeTo) && dateAndTimeFrom.isAfter(LocalDateTime.now());
    }
}
