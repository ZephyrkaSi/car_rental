package by.itacademy.javaenterprise.carrental.silina.service;

import by.itacademy.javaenterprise.carrental.silina.repository.model.Role;
import by.itacademy.javaenterprise.carrental.silina.service.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    Role getRoleById(Long id);

    List<RoleDTO> getAllRoleDTOs();
}
