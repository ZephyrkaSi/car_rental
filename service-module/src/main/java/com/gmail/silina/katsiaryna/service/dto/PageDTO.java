package com.gmail.silina.katsiaryna.service.dto;

import lombok.Data;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageDTO<T> {
    private List<T> content = new ArrayList();
    private Pageable pageable;
    private long total;
}
