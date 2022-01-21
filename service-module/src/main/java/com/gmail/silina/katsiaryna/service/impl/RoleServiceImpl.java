package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.RoleRepository;
import com.gmail.silina.katsiaryna.repository.model.Role;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.RoleService;
import com.gmail.silina.katsiaryna.service.dto.RoleDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;
    private final ConvertService convertService;

    @Override
    public Role getRoleById(Long id) {
        var optionalRole = roleRepository.findById(id);
        return optionalRole.orElse(null);
    }

    @Override
    public List<RoleDTO> getAllRoleDTOs() {
        var roles = roleRepository.findAll();
        return convertService.getDTOsFromObjectList(roles, RoleDTO.class);
    }
}
