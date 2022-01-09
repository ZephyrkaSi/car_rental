package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.CarStatusRepository;
import com.gmail.silina.katsiaryna.repository.model.CarStatus;
import com.gmail.silina.katsiaryna.repository.model.CarStatusEnum;
import com.gmail.silina.katsiaryna.service.CarStatusService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarStatusServiceImpl implements CarStatusService {
    private final CarStatusRepository carStatusRepository;

    @Override
    public CarStatus getCarStatus(CarStatusEnum carStatusEnum) {
        return carStatusRepository.findByStatus(carStatusEnum);
    }
}
