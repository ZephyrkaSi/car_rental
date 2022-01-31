package by.itacademy.javaenterprise.carrental.silina.service.impl.integration.test;

import by.itacademy.javaenterprise.carrental.silina.config.AppConfig;
import by.itacademy.javaenterprise.carrental.silina.repository.UserDetailsRepository;
import by.itacademy.javaenterprise.carrental.silina.service.ConvertService;
import by.itacademy.javaenterprise.carrental.silina.service.UserDetailsService;
import by.itacademy.javaenterprise.carrental.silina.service.dto.UserDetailsDTO;
import by.itacademy.javaenterprise.carrental.silina.service.exception.ServiceException;
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
class UserDetailsServiceImplTest {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Autowired
    private ConvertService convertService;

    static Stream<Arguments> createUserDetailsIdVariety() {
        return Stream.of(
                //Long UserDetailsId, boolean expectUserDetailsInDB
                arguments(1L, true),
                arguments(40L, false),
                arguments(null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("createUserDetailsIdVariety")
    void getUserDetailsById(Long userDetailsId, boolean expectUserDetailsInDB) {
        if (expectUserDetailsInDB) {
            var actualUserDetails = userDetailsService.getUserDetailsById(userDetailsId);
            Assertions.assertNotNull(actualUserDetails);

            var expectedUserDetails = userDetailsRepository.findById(userDetailsId).get();
            Assertions.assertEquals(expectedUserDetails, actualUserDetails);
        } else {
            var serviceException = Assertions.assertThrows(Exception.class, () -> {
                userDetailsService.getUserDetailsById(userDetailsId);
            });

            if (userDetailsId == null) {
                Assertions.assertEquals("User details id cannot be null", serviceException.getMessage());
            } else {
                Assertions.assertEquals("User details with id " + userDetailsId + " doesn't exist", serviceException.getMessage());
            }
        }
    }

    @ParameterizedTest
    @MethodSource("createUserDetailsIdVariety")
    void getUserDetailsDTOById(Long userDetailsId, boolean expectUserDetailsInDB) {
        if (expectUserDetailsInDB) {
            var userDetailsDTO = userDetailsService.getUserDetailsDTOById(userDetailsId);
            Assertions.assertNotNull(userDetailsDTO);
            Assertions.assertEquals(UserDetailsDTO.class, userDetailsDTO.getClass());

            //checking fields
            var actualUserDetails = userDetailsRepository.findById(userDetailsId).get();
            Assertions.assertEquals(actualUserDetails.getId(), userDetailsDTO.getId());
            Assertions.assertEquals(actualUserDetails.getDiscountStatus().getId(), userDetailsDTO.getDiscountStatus().getId());
            Assertions.assertEquals(actualUserDetails.getFirstName(), userDetailsDTO.getFirstName());
            Assertions.assertEquals(actualUserDetails.getLastName(), userDetailsDTO.getLastName());
            Assertions.assertEquals(actualUserDetails.getPassportData(), userDetailsDTO.getPassportData());
            Assertions.assertEquals(actualUserDetails.getTotalRentalTime(), userDetailsDTO.getTotalRentalTime());
        } else {
            var serviceException = Assertions.assertThrows(ServiceException.class, () -> {
                userDetailsService.getUserDetailsDTOById(userDetailsId);
            });

            if (userDetailsId == null) {
                Assertions.assertEquals("User details id cannot be null", serviceException.getMessage());
            } else {
                Assertions.assertEquals("User details with id " + userDetailsId + " doesn't exist", serviceException.getMessage());
            }
        }
    }

    @Test
    void add() {
        var userDetails = userDetailsRepository.findById(1L).get();
        userDetails.setDiscountStatus(null);
        var initialDiscountStatus = userDetails.getDiscountStatus();
        userDetailsService.add(userDetails);
        var resultDiscountStatus = userDetails.getDiscountStatus();
        Assertions.assertNotEquals(initialDiscountStatus, resultDiscountStatus);
    }

    @Test
    void changeUserDetailsFrom() {
        var initialUserDetails = userDetailsRepository.findById(1L).get();
        var initialFirstName = initialUserDetails.getFirstName();
        var initialLastName = initialUserDetails.getLastName();
        var initialPassportData = initialUserDetails.getPassportData();
        var userDetailsDTO = convertService.getDTOFromObject(initialUserDetails, UserDetailsDTO.class);

        var firstNameToChane = "Fname";
        userDetailsDTO.setFirstName(firstNameToChane);
        var lastNameToChange = "Lname";
        userDetailsDTO.setLastName(lastNameToChange);
        var passportDataToChane = "P2919";
        userDetailsDTO.setPassportData(passportDataToChane);

        userDetailsService.changeUserDetailsFrom(userDetailsDTO);

        var changedUserDetails = userDetailsRepository.findById(1L).get();
        var changedFirstName = changedUserDetails.getFirstName();
        Assertions.assertNotEquals(initialFirstName, changedFirstName);
        Assertions.assertEquals(firstNameToChane, changedFirstName);

        var changedLastName = changedUserDetails.getLastName();
        Assertions.assertNotEquals(initialLastName, changedLastName);
        Assertions.assertEquals(lastNameToChange, changedLastName);

        var changedPassportData = changedUserDetails.getPassportData();
        Assertions.assertNotEquals(initialPassportData, changedPassportData);
        Assertions.assertEquals(passportDataToChane, changedPassportData);
    }
}