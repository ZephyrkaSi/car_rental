package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.repository.model.Car;
import com.gmail.silina.katsiaryna.repository.model.CarModel;
import com.gmail.silina.katsiaryna.repository.model.CarStatusEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface CarService {

    List<Car> getAll();

    List<Car> getAvailableCars(CarModel carModel, LocalDateTime begin, LocalDateTime end);

    List<Car> getBrokenCars();

    void changeCarStatus(Car car, CarStatusEnum carStatusEnum);
}
