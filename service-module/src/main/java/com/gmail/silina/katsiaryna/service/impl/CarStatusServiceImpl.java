package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.CarStatusRepository;
import com.gmail.silina.katsiaryna.repository.model.CarStatus;
import com.gmail.silina.katsiaryna.repository.model.CarStatusEnum;
import com.gmail.silina.katsiaryna.service.CarStatusService;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.dto.CarStatusDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CarStatusServiceImpl implements CarStatusService {
    private final CarStatusRepository carStatusRepository;
    private final ConvertService convertService;

    @Override
    public CarStatus getCarStatus(CarStatusEnum carStatusEnum) {
        return carStatusRepository.findByStatus(carStatusEnum);
    }

    @Override
    public CarStatus getCarStatusById(Long id) {
        var optionalCarStatus = carStatusRepository.findById(id);
        return optionalCarStatus.orElse(null);
    }

    @Override
    public List<CarStatusDTO> getAllCarStatusDTOs() {
        var carStatuses = carStatusRepository.findAll();
        return convertService.getDTOsFromObjectList(carStatuses, CarStatusDTO.class);
    }
}
