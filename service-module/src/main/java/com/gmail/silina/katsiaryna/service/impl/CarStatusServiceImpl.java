package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.CarStatusRepository;
import com.gmail.silina.katsiaryna.repository.model.CarStatus;
import com.gmail.silina.katsiaryna.service.CarStatusService;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.dto.CarStatusDTO;
import com.gmail.silina.katsiaryna.service.exception.ServiceException;
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
