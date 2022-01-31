package by.itacademy.javaenterprise.carrental.silina.service.impl.integration.test;

import by.itacademy.javaenterprise.carrental.silina.config.AppConfig;
import by.itacademy.javaenterprise.carrental.silina.repository.DiscountStatusRepository;
import by.itacademy.javaenterprise.carrental.silina.repository.model.DiscountStatusEnum;
import by.itacademy.javaenterprise.carrental.silina.service.DiscountStatusService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
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

@DataJpaTest
@Sql(scripts = {"classpath:/schema.sql", "/data.sql"})
@ContextConfiguration(classes = {AppConfig.class, IntegrationConfig.class})
@EnableJpaRepositories(basePackages = {"by.itacademy.javaenterprise.carrental.silina.repository"})
@EntityScan("by.itacademy.javaenterprise.carrental.silina.repository.model")
@TestPropertySource(locations = {"classpath:application.properties"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class DiscountStatusServiceImplTest {
    @Autowired
    private DiscountStatusService discountStatusService;
    @Autowired
    private DiscountStatusRepository discountStatusRepository;

    static Stream<DiscountStatusEnum> createDiscountStatusEnumVariety() {
        return Stream.of(
                DiscountStatusEnum.BRILLIANT,
                DiscountStatusEnum.BRONZE,
                DiscountStatusEnum.GOLD,
                DiscountStatusEnum.SILVER,
                null
        );
    }

    @ParameterizedTest
    @MethodSource("createDiscountStatusEnumVariety")
    void getDiscountStatus(DiscountStatusEnum discountStatusEnum) {
        if (discountStatusEnum == null) {
            var serviceException = Assertions.assertThrows(Exception.class, () -> {
                discountStatusService.getDiscountStatus(discountStatusEnum);
            });
            Assertions.assertEquals("DiscountStatusEnum cannot be null", serviceException.getMessage());
        } else {
            var actualDiscountStatus = discountStatusService.getDiscountStatus(discountStatusEnum);
            Assertions.assertNotNull(actualDiscountStatus);
            var expectedDiscountStatus = discountStatusRepository.findByStatus(discountStatusEnum);
            Assertions.assertEquals(expectedDiscountStatus, actualDiscountStatus);
        }
    }
}