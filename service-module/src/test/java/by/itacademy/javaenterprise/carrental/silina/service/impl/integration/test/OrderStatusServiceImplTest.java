package by.itacademy.javaenterprise.carrental.silina.service.impl.integration.test;

import by.itacademy.javaenterprise.carrental.silina.config.AppConfig;
import by.itacademy.javaenterprise.carrental.silina.repository.OrderStatusRepository;
import by.itacademy.javaenterprise.carrental.silina.repository.model.OrderStatusEnum;
import by.itacademy.javaenterprise.carrental.silina.service.ConvertService;
import by.itacademy.javaenterprise.carrental.silina.service.OrderStatusService;
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

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

@DataJpaTest
@Sql(scripts = {"classpath:/schema.sql", "/data.sql"})
@ContextConfiguration(classes = {AppConfig.class, IntegrationConfig.class})
@EnableJpaRepositories(basePackages = {"by.itacademy.javaenterprise.carrental.silina.repository"})
@EntityScan("by.itacademy.javaenterprise.carrental.silina.repository.model")
@TestPropertySource(locations = {"classpath:application.properties"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderStatusServiceImplTest {
    @Autowired
    private OrderStatusRepository orderStatusRepository;
    @Autowired
    private OrderStatusService orderStatusService;
    @Autowired
    private ConvertService convertService;

    static Stream<Arguments> createOrderStatusIdVariety() {
        return Stream.of(
                //Long orderStatusId, boolean expectOrderStatusInDB
                arguments(1L, true),
                arguments(20L, false),
                arguments(null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("createOrderStatusIdVariety")
    void getOrderStatusById(Long orderStatusId, boolean expectOrderStatusInDB) {
        if (expectOrderStatusInDB) {
            var actualOrderStatus = orderStatusService.getOrderStatusById(orderStatusId);
            Assertions.assertNotNull(actualOrderStatus);

            var expectedOrderStatus = orderStatusRepository.findById(orderStatusId).get();
            Assertions.assertEquals(expectedOrderStatus, actualOrderStatus);
        } else {
            var serviceException = Assertions.assertThrows(Exception.class, () -> {
                orderStatusService.getOrderStatusById(orderStatusId);
            });

            if (orderStatusId == null) {
                Assertions.assertEquals("Order status id cannot be null", serviceException.getMessage());
            } else {
                Assertions.assertEquals("Order status with id " + orderStatusId + " doesn't exist", serviceException.getMessage());
            }
        }
    }

    @Test
    void getOrderStatus() {
        var expectedOrderStatusEnum = OrderStatusEnum.WAITING_FOR_PAYMENT;
        var orderStatus = orderStatusService.getOrderStatus(expectedOrderStatusEnum);
        var actualOrderStatusEnum = orderStatus.getOrderStatus();
        Assertions.assertEquals(expectedOrderStatusEnum, actualOrderStatusEnum);
    }

    @Test
    void getAllStatuses() {
        int expectedNumberOfStatuses = orderStatusRepository.findAll().size();
        int actualNumberOfStatuses = orderStatusService.getAllStatuses().size();
        Assertions.assertEquals(expectedNumberOfStatuses, actualNumberOfStatuses);
    }

    //todo
    @Test
    void getEligibleStatusesForOrder() {
    }
}