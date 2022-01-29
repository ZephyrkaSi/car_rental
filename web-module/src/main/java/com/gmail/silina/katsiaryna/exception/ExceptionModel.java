package com.gmail.silina.katsiaryna.exception;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExceptionModel {
    private int errorNumber;
    private String errorMessage;
}
