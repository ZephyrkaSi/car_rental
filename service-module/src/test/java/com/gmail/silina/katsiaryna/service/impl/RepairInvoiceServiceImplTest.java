package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.config.AppConfig;
import com.gmail.silina.katsiaryna.repository.CarRepository;
import com.gmail.silina.katsiaryna.repository.RepairInvoiceRepository;
import com.gmail.silina.katsiaryna.repository.UserRepository;
import com.gmail.silina.katsiaryna.repository.model.Car;
import com.gmail.silina.katsiaryna.repository.model.User;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.RepairInvoiceService;
import com.gmail.silina.katsiaryna.service.UserService;
import com.gmail.silina.katsiaryna.service.dto.CarDTO;
import com.gmail.silina.katsiaryna.service.dto.RepairInvoiceDTO;
import com.gmail.silina.katsiaryna.service.dto.UserDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;

@DataJpaTest
@Sql(scripts = {"classpath:/schema.sql", "/data.sql"})
@ContextConfiguration(classes = {AppConfig.class, IntegrationConfig.class})
@EnableJpaRepositories(basePackages = {"com.gmail.silina.katsiaryna.repository"})
@EntityScan("com.gmail.silina.katsiaryna.repository.model")
@TestPropertySource(locations = {"classpath:application.properties"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RepairInvoiceServiceImplTest {
    @Autowired
    private RepairInvoiceService repairInvoiceService;
    @Autowired
    private RepairInvoiceRepository repairInvoiceRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ConvertService convertService;

    @MockBean
    private UserService userService;

    @Test
    void getAllRepairInvoiceDTOs() {
        var expectedInvoices = repairInvoiceRepository.findAll();
        var actualInvoiceDTOs = repairInvoiceService.getAllRepairInvoiceDTOs();
        Assertions.assertEquals(expectedInvoices.size(), actualInvoiceDTOs.size());
    }

    @Test
    void addRepairInvoice() {
        //preparing for test
        RepairInvoiceDTO repairInvoiceDTO = new RepairInvoiceDTO();

        Long carId = 1L;
        Car car = carRepository.findById(carId).get();
        CarDTO carDTO = convertService.getDTOFromObject(car, CarDTO.class);
        repairInvoiceDTO.setCar(carDTO);

        Long adminId = 1L;
        Mockito.when(userService.getPrincipalUserId()).thenReturn(adminId);
        User admin = userRepository.findById(adminId).get();
        Mockito.when(userService.getUserById(adminId)).thenReturn(admin);

        Long userId = 3L;
        User user = userRepository.findById(userId).get();
        Mockito.when(userService.getUserById(userId)).thenReturn(user);

        UserDTO userDTO = convertService.getDTOFromObject(user, UserDTO.class);
        repairInvoiceDTO.setClient(userDTO);

        repairInvoiceDTO.setCost(BigDecimal.valueOf(1200));
        repairInvoiceDTO.setDamageInfo("Damage info");

        //test
        int initialNumberOfInvoices = repairInvoiceRepository.findAll().size();
        repairInvoiceService.addRepairInvoice(repairInvoiceDTO);
        int resultNumberOfInvoices = repairInvoiceRepository.findAll().size();
        Assertions.assertEquals(initialNumberOfInvoices, resultNumberOfInvoices - 1);
    }
}
