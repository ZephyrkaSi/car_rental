package com.gmail.silina.katsiaryna.service.dto;

import com.gmail.silina.katsiaryna.repository.model.CarModel;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Validated
public class OrderFormDTO {
    @NotNull(message = "Date should not be valid")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime dateAndTimeFrom;
    @NotNull(message = "Date should not be valid")
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
