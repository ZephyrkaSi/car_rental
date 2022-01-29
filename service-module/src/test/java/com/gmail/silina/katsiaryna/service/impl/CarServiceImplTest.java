package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.config.AppConfig;
import com.gmail.silina.katsiaryna.repository.CarRepository;
import com.gmail.silina.katsiaryna.repository.CarStatusRepository;
import com.gmail.silina.katsiaryna.service.CarService;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.dto.CarDTO;
import com.gmail.silina.katsiaryna.service.dto.CarStatusDTO;
import com.gmail.silina.katsiaryna.service.exception.ServiceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@DataJpaTest
@Sql(scripts = {"classpath:/schema.sql", "/data.sql"})
@ContextConfiguration(classes = {AppConfig.class, IntegrationConfig.class})
@EnableJpaRepositories(basePackages = {"com.gmail.silina.katsiaryna.repository"})
@EntityScan("com.gmail.silina.katsiaryna.repository.model")
@TestPropertySource(locations = {"classpath:application.properties"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarServiceImplTest {
    private final CarDTO carDTO = new CarDTO();
    private final CarStatusDTO carStatusDTO = new CarStatusDTO();
    @Autowired
    private CarService carService;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private CarStatusRepository carStatusRepository;
    @Autowired
    private ConvertService convertService;

    static Stream<Arguments> createCarIdVariety() {
        return Stream.of(
                //Long carId, boolean expectCarInDB
                arguments(1L, true),
                arguments(20L, false),
                arguments(null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("createCarIdVariety")
    void getCarById(Long carId, boolean expectCarInDB) {
        if (expectCarInDB) {
            var actualCar = carService.getCarById(carId);
            Assertions.assertNotNull(actualCar);

            var expectedCar = carRepository.findById(carId).get();
            Assertions.assertEquals(expectedCar, actualCar);
        } else {
            var serviceException = Assertions.assertThrows(Exception.class, () -> {
                carService.getCarById(carId);
            });

            if (carId == null) {
                Assertions.assertEquals("Car id cannot be null", serviceException.getMessage());
            } else {
                Assertions.assertEquals("Car with id " + carId + " doesn't exist", serviceException.getMessage());
            }
        }
    }

    @ParameterizedTest
    @MethodSource("createCarIdVariety")
    void getCarDTOById(Long carId, boolean expectCarInDB) {
        if (expectCarInDB) {
            var carDTO = carService.getCarDTOById(carId);
            Assertions.assertNotNull(carDTO);
            Assertions.assertEquals(CarDTO.class, carDTO.getClass());

            //checking fields
            var actualCar = carRepository.findById(carId).get();
            Assertions.assertEquals(actualCar.getId(), carDTO.getId());
            Assertions.assertEquals(actualCar.getStateNumber(), carDTO.getStateNumber());
            Assertions.assertEquals(actualCar.getCarModel().getModel(), carDTO.getCarModel().getModel());
            Assertions.assertEquals(actualCar.getCarStatus().getId(), carDTO.getCarStatus().getId());
        } else {
            var serviceException = Assertions.assertThrows(ServiceException.class, () -> {
                carService.getCarDTOById(carId);
            });

            if (carId == null) {
                Assertions.assertEquals("Car id cannot be null", serviceException.getMessage());
            } else {
                Assertions.assertEquals("Car with id " + carId + " doesn't exist", serviceException.getMessage());
            }
        }
    }

    @Test
    void getAll() {
        var expectedCars = carRepository.findAll();
        var resultCars = carService.getAll();
        Assertions.assertEquals(expectedCars, resultCars);
    }

    @Test
    void getAllCarDTOs() {
        var cars = carRepository.findAll();
        var expectedCarDTOs = convertService.getDTOsFromObjectList(cars, CarDTO.class);
        var resultCarDTOs = carService.getAllCarDTOs();
        Assertions.assertEquals(expectedCarDTOs, resultCarDTOs);
    }

    @Test
    void getAllCarDTOsByPage() {
        Pageable pageable = Pageable.unpaged();
        var cars = carRepository.findAll(pageable);
        var expectedCarDTOs = convertService.getPageDTOFromPage(cars, CarDTO.class);
        var resultCarDTOs = carService.getAllCarDTOsByPage(pageable);
        Assertions.assertEquals(expectedCarDTOs, resultCarDTOs);
    }

    @Test
    void getAvailableCars() {
        var begin = LocalDateTime.of(2017, Month.JULY, 9, 11, 10, 0);
        var end = LocalDateTime.of(2017, Month.JULY, 9, 18, 20, 0);
        var expectedAvailableCars = carRepository.findAvailableCars(1L, begin, end);
        var availableCars = carService.getAvailableCars(1L, begin, end);
        Assertions.assertEquals(expectedAvailableCars, availableCars);
    }

    @Test
    void updateCarStatusFrom() {
        //preparation to test
        Long statusId = 3L;
        var carStatus = carStatusRepository.findById(statusId).get();
        carStatusDTO.setId(statusId);
        carStatusDTO.setCarStatus(carStatus.getCarStatus().name());

        Long carId = 1L;
        carDTO.setId(carId);
        carDTO.setCarStatus(carStatusDTO);

        var car = carRepository.findById(carId).get();

        //testing
        Assertions.assertNotEquals(car.getCarStatus(), carStatus);

        //todo not clear what is happening in this test
        carService.updateCarStatusFrom(carDTO);
        Assertions.assertEquals(car.getCarStatus(), carStatus);
    }
}
