package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.RoleRepository;
import com.gmail.silina.katsiaryna.repository.model.Role;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.RoleService;
import com.gmail.silina.katsiaryna.service.dto.RoleDTO;
import com.gmail.silina.katsiaryna.service.exception.ServiceException;
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
