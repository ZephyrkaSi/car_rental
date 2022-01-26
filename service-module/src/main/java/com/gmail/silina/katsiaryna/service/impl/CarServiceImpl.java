package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.CarRepository;
import com.gmail.silina.katsiaryna.repository.model.Car;
import com.gmail.silina.katsiaryna.service.CarService;
import com.gmail.silina.katsiaryna.service.CarStatusService;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.dto.CarDTO;
import com.gmail.silina.katsiaryna.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;
    private final CarStatusService carStatusService;
    private final ConvertService convertService;

    @Override
    public Car getCarById(Long id) {
        if (id == null) {
            log.error("Car id cannot be null");
            throw new ServiceException("Car id cannot be null");
        } else {
            var optionalCar = carRepository.findById(id);
            if (optionalCar.isPresent()) {
                return optionalCar.get();
            } else {
                log.error("Car with id {} doesn't exist", id);
                throw new ServiceException("Car with id " + id + " doesn't exist");
            }
        }
    }

    @Override
    public CarDTO getCarDTOById(Long id) {
        if (id == null) {
            log.error("Car id cannot be null");
            throw new ServiceException("Car id cannot be null");
        } else {
            var car = getCarById(id);
            if (car == null) {
                log.error("Car with id {} doesn't exist", id);
                throw new ServiceException("Car with id " + id + " doesn't exist");
            } else {
                return convertService.getDTOFromObject(car, CarDTO.class);
            }
        }
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
    public List<Car> getAvailableCars(Long carModelId, LocalDateTime begin, LocalDateTime end) {
        return carRepository.findAvailableCars(carModelId, begin, end);
    }

    @Override
    public void updateCarStatusFrom(CarDTO carDTO) {
        var carId = carDTO.getId();
        var car = getCarById(carId);

        var carStatusId = carDTO.getCarStatus().getId();
        var carStatus = carStatusService.getCarStatusById(carStatusId);
        car.setCarStatus(carStatus);
        log.info("Changing car status with id {}", carId);
        carRepository.save(car);
    }
}
