package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.repository.model.CarStatus;
import com.gmail.silina.katsiaryna.repository.model.CarStatusEnum;
import com.gmail.silina.katsiaryna.service.dto.CarStatusDTO;

import java.util.List;

public interface CarStatusService {
    
    CarStatus getCarStatus(CarStatusEnum carStatusEnum);

    CarStatus getCarStatusById(Long id);

    List<CarStatusDTO> getAllCarStatusDTOs();
}
