package by.itacademy.javaenterprise.carrental.silina.service.impl;

import by.itacademy.javaenterprise.carrental.silina.config.AppConfig;
import by.itacademy.javaenterprise.carrental.silina.repository.CarRepository;
import by.itacademy.javaenterprise.carrental.silina.repository.RepairInvoiceRepository;
import by.itacademy.javaenterprise.carrental.silina.repository.UserRepository;
import by.itacademy.javaenterprise.carrental.silina.repository.model.Car;
import by.itacademy.javaenterprise.carrental.silina.repository.model.User;
import by.itacademy.javaenterprise.carrental.silina.service.ConvertService;
import by.itacademy.javaenterprise.carrental.silina.service.RepairInvoiceService;
import by.itacademy.javaenterprise.carrental.silina.service.UserService;
import by.itacademy.javaenterprise.carrental.silina.service.dto.CarDTO;
import by.itacademy.javaenterprise.carrental.silina.service.dto.RepairInvoiceDTO;
import by.itacademy.javaenterprise.carrental.silina.service.dto.UserDTO;
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
@EnableJpaRepositories(basePackages = {"by.itacademy.javaenterprise.carrental.silina.repository"})
@EntityScan("by.itacademy.javaenterprise.carrental.silina.repository.model")
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

    //todo why compare only size?
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
