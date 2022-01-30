package by.itacademy.javaenterprise.carrental.silina.service.impl;

import by.itacademy.javaenterprise.carrental.silina.repository.CarStatusRepository;
import by.itacademy.javaenterprise.carrental.silina.repository.model.CarStatus;
import by.itacademy.javaenterprise.carrental.silina.service.CarStatusService;
import by.itacademy.javaenterprise.carrental.silina.service.ConvertService;
import by.itacademy.javaenterprise.carrental.silina.service.dto.CarStatusDTO;
import by.itacademy.javaenterprise.carrental.silina.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class CarStatusServiceImpl implements CarStatusService {
    private final CarStatusRepository carStatusRepository;
    private final ConvertService convertService;

    @Override
    public CarStatus getCarStatusById(Long id) {
        if (id == null) {
            log.error("Car status id cannot be null");
            throw new ServiceException("Car status id cannot be null");
        } else {
            var optionalCarStatus = carStatusRepository.findById(id);
            if (optionalCarStatus.isPresent()) {
                return optionalCarStatus.get();
            } else {
                log.error("Car status with id {} doesn't exist", id);
                throw new ServiceException("Car status with id " + id + " doesn't exist");
            }
        }
    }

    @Override
    public List<CarStatusDTO> getAllCarStatusDTOs() {
        var carStatuses = carStatusRepository.findAll();
        return convertService.getDTOsFromObjectList(carStatuses, CarStatusDTO.class);
    }
}
