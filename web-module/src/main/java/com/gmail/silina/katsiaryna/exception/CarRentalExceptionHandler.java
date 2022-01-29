package com.gmail.silina.katsiaryna.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@ControllerAdvice(basePackages = "com.gmail.silina.katsiaryna")
public class CarRentalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    protected ResponseEntity<ExceptionModel> handle(Exception ex) {
        var badRequest = HttpStatus.BAD_REQUEST;
        var exceptionModel = ExceptionModel.builder()
                .errorNumber(badRequest.value())
                .errorMessage(ex.getMessage())
                .build();
        log.error(ex.getMessage());
        return new ResponseEntity<>(exceptionModel, badRequest);
    }
}
