package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.config.AppConfig;
import com.gmail.silina.katsiaryna.repository.CarStatusRepository;
import com.gmail.silina.katsiaryna.repository.model.CarStatus;
import com.gmail.silina.katsiaryna.service.CarStatusService;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.dto.CarStatusDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@DataJpaTest
@Sql(scripts = {"classpath:/schema.sql", "/data.sql"})
@ContextConfiguration(classes = {AppConfig.class, IntegrationConfig.class})
@EnableJpaRepositories(basePackages = {"com.gmail.silina.katsiaryna.repository"})
@EntityScan("com.gmail.silina.katsiaryna.repository.model")
@TestPropertySource(locations = {"classpath:application.properties"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarStatusServiceImplTest {
    @Autowired
    private CarStatusService carStatusService;
    @Autowired
    private CarStatusRepository carStatusRepository;
    @Autowired
    private ConvertService convertService;

    static Stream<Arguments> createCarStatusIdVariety() {
        return Stream.of(
                //Long carId, boolean expectCarStatusInDB
                arguments(1L, true),
                arguments(20L, false),
                arguments(null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("createCarStatusIdVariety")
    void getCarStatusById(Long carStatusId, boolean expectCarStatusInDB) {
        if (expectCarStatusInDB) {
            var actualCarStatus = carStatusService.getCarStatusById(carStatusId);
            Assertions.assertNotNull(actualCarStatus);

            var expectedCarStatus = carStatusRepository.findById(carStatusId).get();
            Assertions.assertEquals(expectedCarStatus, actualCarStatus);
        } else {
            var serviceException = Assertions.assertThrows(Exception.class, () -> {
                carStatusService.getCarStatusById(carStatusId);
            });

            if (carStatusId == null) {
                Assertions.assertEquals("Car status id cannot be null", serviceException.getMessage());
            } else {
                Assertions.assertEquals("Car status with id " + carStatusId + " doesn't exist", serviceException.getMessage());
            }
        }
    }

    @Test
    void getAllCarStatusDTOs() {
        List<CarStatus> carStatuses = carStatusRepository.findAll();
        List<CarStatusDTO> expectedCarStatusDTOs = convertService.getDTOsFromObjectList(carStatuses, CarStatusDTO.class);
        List<CarStatusDTO> resultCarStatusDTOs = carStatusService.getAllCarStatusDTOs();
        Assertions.assertEquals(expectedCarStatusDTOs, resultCarStatusDTOs);
    }
}