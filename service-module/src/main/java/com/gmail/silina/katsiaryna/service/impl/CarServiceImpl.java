package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.CarRepository;
import com.gmail.silina.katsiaryna.repository.model.Car;
import com.gmail.silina.katsiaryna.repository.model.CarModel;
import com.gmail.silina.katsiaryna.repository.model.CarStatusEnum;
import com.gmail.silina.katsiaryna.service.CarService;
import com.gmail.silina.katsiaryna.service.CarStatusService;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.dto.CarDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarStatusService carStatusService;
    private final ConvertService convertService;

    @Override
    public Car getCarById(Long id) {
        var optionalCar = carRepository.findById(id);
        return optionalCar.orElse(null);
    }

    @Override
    public CarDTO getCarDTOById(Long id) {
        var car = getCarById(id);
        return convertService.getDTOFromObject(car, CarDTO.class);
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }

    @Override
    public List<CarDTO> getAllCarDTOs() {
        var cars = getAll();
        return convertService.getDTOsFromObjectList(cars, CarDTO.class);
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
    public void updateCarStatusFrom(CarDTO carDTO) {
        var carId = carDTO.getId();
        var car = getCarById(carId);

        var carStatusId = carDTO.getCarStatus().getId();
        var carStatus = carStatusService.getCarStatusById(carStatusId);
        car.setCarStatus(carStatus);
        carRepository.save(car);
    }
}
