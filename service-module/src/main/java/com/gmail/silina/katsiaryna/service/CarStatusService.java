package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.repository.model.CarStatus;
import com.gmail.silina.katsiaryna.repository.model.CarStatusEnum;

public interface CarStatusService {
    CarStatus getCarStatus(CarStatusEnum carStatusEnum);

}
