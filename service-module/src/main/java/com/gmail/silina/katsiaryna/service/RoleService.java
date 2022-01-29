package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.repository.model.Role;
import com.gmail.silina.katsiaryna.service.dto.RoleDTO;

import java.util.List;

public interface RoleService {

    Role getRoleById(Long id);

    List<RoleDTO> getAllRoleDTOs();
}
