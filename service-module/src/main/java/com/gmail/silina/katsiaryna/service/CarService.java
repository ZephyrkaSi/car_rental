package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.repository.model.Car;
import com.gmail.silina.katsiaryna.repository.model.CarModel;
import com.gmail.silina.katsiaryna.service.dto.CarDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface CarService {

    Car getCarById(Long id);

    CarDTO getCarDTOById(Long id);

    List<Car> getAll();

    List<CarDTO> getAllCarDTOs();


    List<Car> getAvailableCars(CarModel carModel, LocalDateTime begin, LocalDateTime end);

    List<Car> getBrokenCars();

    void updateCarStatusFrom(CarDTO carDTO);
}
