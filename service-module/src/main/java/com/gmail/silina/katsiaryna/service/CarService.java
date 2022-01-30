package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.repository.model.Car;
import com.gmail.silina.katsiaryna.service.dto.CarDTO;
import com.gmail.silina.katsiaryna.service.dto.PageDTO;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface CarService {

    Car getCarById(Long id);

    CarDTO getCarDTOById(Long id);

    List<Car> getAll();

    List<CarDTO> getAllCarDTOs();

    PageDTO<CarDTO> getAllCarDTOsByPage(Pageable pageable);

    List<Car> getAvailableCars(Long carModelId, LocalDateTime begin, LocalDateTime end);

    void updateCarStatusFrom(CarDTO carDTO);

    CarDTO updateStateNumber(Long carId, String stateNumber);
}
