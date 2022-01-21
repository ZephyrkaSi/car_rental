package com.gmail.silina.katsiaryna.service.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ErrorDTO {
    private List<String> errors = new ArrayList<>();

    public void add(String errorMessage) {
        errors.add(errorMessage);
    }
}
