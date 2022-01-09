package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.CarRepository;
import com.gmail.silina.katsiaryna.repository.model.Car;
import com.gmail.silina.katsiaryna.repository.model.CarModel;
import com.gmail.silina.katsiaryna.repository.model.CarStatusEnum;
import com.gmail.silina.katsiaryna.service.CarService;
import com.gmail.silina.katsiaryna.service.CarStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarStatusService carStatusService;

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public List<Car> getAvailableCars(CarModel carModel, LocalDateTime begin, LocalDateTime end) {
        return carRepository.findAvailableCars(carModel.getId(), begin, end);
    }

    @Override
    public List<Car> getBrokenCars() {
        var brokenCarStatus = carStatusService.getCarStatus(CarStatusEnum.BROKEN);
        return carRepository.getCarsByStatus(brokenCarStatus);
    }

    @Override
    public void changeCarStatus(Car car, CarStatusEnum carStatusEnum) {

    }
}
