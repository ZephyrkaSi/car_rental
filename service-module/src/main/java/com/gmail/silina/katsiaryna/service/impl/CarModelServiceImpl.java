package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.CarModelRepository;
import com.gmail.silina.katsiaryna.repository.model.CarModel;
import com.gmail.silina.katsiaryna.service.CarModelService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarModelServiceImpl implements CarModelService {
    private final CarModelRepository carModelRepository;

    @Override
    public List<CarModel> getAll() {
        return carModelRepository.findAll();
    }
}
