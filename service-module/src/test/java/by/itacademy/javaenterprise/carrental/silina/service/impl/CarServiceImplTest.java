package by.itacademy.javaenterprise.carrental.silina.service.impl;

import by.itacademy.javaenterprise.carrental.silina.repository.CarRepository;
import by.itacademy.javaenterprise.carrental.silina.repository.model.Car;
import by.itacademy.javaenterprise.carrental.silina.repository.model.CarModel;
import by.itacademy.javaenterprise.carrental.silina.repository.model.CarStatus;
import by.itacademy.javaenterprise.carrental.silina.repository.model.CarStatusEnum;
import by.itacademy.javaenterprise.carrental.silina.service.CarService;
import by.itacademy.javaenterprise.carrental.silina.service.CarStatusService;
import by.itacademy.javaenterprise.carrental.silina.service.ConvertService;
import by.itacademy.javaenterprise.carrental.silina.service.dto.CarDTO;
import by.itacademy.javaenterprise.carrental.silina.service.dto.CarModelDTO;
import by.itacademy.javaenterprise.carrental.silina.service.dto.CarStatusDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class, SpringExtension.class})
@ContextConfiguration(classes = {CarServiceImpl.class})
class CarServiceImplTest {
    @MockBean
    private CarRepository carRepository;
    @Autowired
    private CarService carService;

    @Test
    void getCarById() {
        Long carId = 1L;

        CarStatus carStatus = new CarStatus();
        carStatus.setId(carId);
        carStatus.setCarStatus(CarStatusEnum.READY);

        CarModel carModel = new CarModel();
        carModel.setId(1L);
        carModel.setModel("Model");
        carModel.setBodyColor("Colour");
        carModel.setBodyType("Type");
        carModel.setBrandName("Brand");
        carModel.setEngineVolume(1.6);
        carModel.setInteriorColor("Colour");
        carModel.setFuelType("Type");
        carModel.setPricePerMinute(2);
        carModel.setTransmission("Transmission");

        Car car = new Car();
        car.setId(carId);
        car.setStateNumber("1234SN-7");
        car.setCarStatus(carStatus);
        car.setCarModel(carModel);
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        Car expectedCar = carRepository.findById(carId).get();
        Car actualCar = carService.getCarById(carId);
        Assertions.assertEquals(expectedCar, actualCar);
    }

    @Test
    void getCarDTOById() {
        Long carId = 1L;

        CarStatus carStatus = new CarStatus();
        carStatus.setId(carId);
        carStatus.setCarStatus(CarStatusEnum.READY);

        CarModel carModel = new CarModel();
        carModel.setId(1L);
        carModel.setModel("Model");
        carModel.setBodyColor("Colour");
        carModel.setBodyType("Type");
        carModel.setBrandName("Brand");
        carModel.setEngineVolume(1.6);
        carModel.setInteriorColor("Colour");
        carModel.setFuelType("Type");
        carModel.setPricePerMinute(2);
        carModel.setTransmission("Transmission");

        Car car = new Car();
        car.setId(carId);
        car.setStateNumber("1234SN-7");
        car.setCarStatus(carStatus);
        car.setCarModel(carModel);
        when(carRepository.findById(carId)).thenReturn(Optional.of(car));

        CarDTO expectedCarDTO = new CarDTO();
        CarStatusDTO carStatusDTO = new CarStatusDTO();
        CarModelDTO carModelDTO = new CarModelDTO();

        carStatusDTO.setCarStatus(car.getCarStatus().getCarStatus().name());
        carModelDTO.setModel(car.getCarModel().getModel());

        expectedCarDTO.setCarStatus(carStatusDTO);
        expectedCarDTO.setCarModel(carModelDTO);
        expectedCarDTO.setId(car.getId());
        expectedCarDTO.setStateNumber(car.getStateNumber());

        when(convertService.getDTOFromObject(car, CarDTO.class)).thenReturn(expectedCarDTO);

        convertService.getDTOFromObject(car, CarDTO.class);

        CarDTO actualCarDTO = carService.getCarDTOById(carId);
        Assertions.assertEquals(expectedCarDTO, actualCarDTO);
    }

    @Test
    void getAll() {
    }

    @Test
    void getAllCarDTOs() {
    }

    @Test
    void getAllCarDTOsByPage() {
    }

    @Test
    void getAvailableCars() {
    }

    @Test
    void updateCarStatusFrom() {
    }

    @Test
    void updateStateNumber() {
    }
}