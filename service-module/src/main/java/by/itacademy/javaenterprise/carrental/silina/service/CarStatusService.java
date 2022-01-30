package by.itacademy.javaenterprise.carrental.silina.service;

import by.itacademy.javaenterprise.carrental.silina.repository.model.CarStatus;
import by.itacademy.javaenterprise.carrental.silina.service.dto.CarStatusDTO;

import java.util.List;

public interface CarStatusService {

    CarStatus getCarStatusById(Long id);

    List<CarStatusDTO> getAllCarStatusDTOs();
}
