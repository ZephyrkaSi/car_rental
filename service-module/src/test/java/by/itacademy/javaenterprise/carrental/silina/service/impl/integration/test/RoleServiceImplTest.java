package by.itacademy.javaenterprise.carrental.silina.service.impl.integration.test;

import by.itacademy.javaenterprise.carrental.silina.config.AppConfig;
import by.itacademy.javaenterprise.carrental.silina.repository.RoleRepository;
import by.itacademy.javaenterprise.carrental.silina.service.ConvertService;
import by.itacademy.javaenterprise.carrental.silina.service.RoleService;
import by.itacademy.javaenterprise.carrental.silina.service.dto.RoleDTO;
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
class RoleServiceImplTest {
    @Autowired
    private RoleService roleService;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ConvertService convertService;


    static Stream<Arguments> createRoleIdVariety() {
        return Stream.of(
                //Long orderId, boolean expectRoleInDB
                arguments(1L, true),
                arguments(2L, true),
                arguments(20L, false),
                arguments(null, false)
        );
    }

    @ParameterizedTest
    @MethodSource("createRoleIdVariety")
    void getRoleById(Long roleId, boolean expectRoleInDB) {
        if (expectRoleInDB) {
            var actualRole = roleService.getRoleById(roleId);
            Assertions.assertNotNull(actualRole);

            var expectedRole = roleRepository.findById(roleId).get();
            Assertions.assertEquals(expectedRole, actualRole);
        } else {
            var serviceException = Assertions.assertThrows(Exception.class, () -> {
                roleService.getRoleById(roleId);
            });

            if (roleId == null) {
                Assertions.assertEquals("Role id cannot be null", serviceException.getMessage());
            } else {
                Assertions.assertEquals("Role with id " + roleId + " doesn't exist", serviceException.getMessage());
            }
        }
    }

    @Test
    void getAllRoleDTOs() {
        var roles = roleRepository.findAll();
        var expectedRoleDTOs = convertService.getDTOsFromObjectList(roles, RoleDTO.class);
        var resultRoleDTOs = roleService.getAllRoleDTOs();
        Assertions.assertEquals(expectedRoleDTOs, resultRoleDTOs);
    }
}