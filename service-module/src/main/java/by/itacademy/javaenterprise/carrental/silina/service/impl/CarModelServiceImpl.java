package by.itacademy.javaenterprise.carrental.silina.service.impl;

import by.itacademy.javaenterprise.carrental.silina.repository.CarModelRepository;
import by.itacademy.javaenterprise.carrental.silina.repository.model.CarModel;
import by.itacademy.javaenterprise.carrental.silina.service.CarModelService;
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
