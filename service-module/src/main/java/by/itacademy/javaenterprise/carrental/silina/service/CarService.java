package by.itacademy.javaenterprise.carrental.silina.service;

import by.itacademy.javaenterprise.carrental.silina.repository.model.Car;
import by.itacademy.javaenterprise.carrental.silina.service.dto.CarDTO;
import by.itacademy.javaenterprise.carrental.silina.service.dto.PageDTO;
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
