package by.itacademy.javaenterprise.carrental.silina.service.impl;

import by.itacademy.javaenterprise.carrental.silina.repository.RoleRepository;
import by.itacademy.javaenterprise.carrental.silina.repository.model.Role;
import by.itacademy.javaenterprise.carrental.silina.service.ConvertService;
import by.itacademy.javaenterprise.carrental.silina.service.RoleService;
import by.itacademy.javaenterprise.carrental.silina.service.dto.RoleDTO;
import by.itacademy.javaenterprise.carrental.silina.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ConvertService convertService;

    @Override
    public Role getRoleById(Long id) {
        if (id == null) {
            log.error("Role id cannot be null");
            throw new ServiceException("Role id cannot be null");
        } else {
            var optionalRole = roleRepository.findById(id);
            if (optionalRole.isPresent()) {
                return optionalRole.get();
            } else {
                log.error("Role with id {} doesn't exist", id);
                throw new ServiceException("Role with id " + id + " doesn't exist");
            }
        }
    }

    @Override
    public List<RoleDTO> getAllRoleDTOs() {
        var roles = roleRepository.findAll();
        return convertService.getDTOsFromObjectList(roles, RoleDTO.class);
    }
}
