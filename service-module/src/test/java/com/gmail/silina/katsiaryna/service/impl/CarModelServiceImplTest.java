package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.config.AppConfig;
import com.gmail.silina.katsiaryna.repository.CarModelRepository;
import com.gmail.silina.katsiaryna.repository.model.CarModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;

@DataJpaTest
@Sql(scripts = {"classpath:/schema.sql", "/data.sql"})
@ContextConfiguration(classes = {AppConfig.class, IntegrationConfig.class})
@EnableJpaRepositories(basePackages = {"com.gmail.silina.katsiaryna.repository"})
@EntityScan("com.gmail.silina.katsiaryna.repository.model")
@TestPropertySource(locations = {"classpath:application.properties"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CarModelServiceImplTest {
    @Autowired
    private CarModelRepository carModelRepository;
    @Autowired
    private CarModelServiceImpl carModelService;

    @Test
    void getAll() {
        List<CarModel> expected = carModelRepository.findAll();
        List<CarModel> result = carModelService.getAll();
        Assertions.assertEquals(expected, result);
    }
}