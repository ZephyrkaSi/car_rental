package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.RoleRepository;
import com.gmail.silina.katsiaryna.repository.UserRepository;
import com.gmail.silina.katsiaryna.repository.model.Role;
import com.gmail.silina.katsiaryna.repository.model.RoleEnum;
import com.gmail.silina.katsiaryna.repository.model.User;
import com.gmail.silina.katsiaryna.service.ConvertService;
import com.gmail.silina.katsiaryna.service.UserService;
import com.gmail.silina.katsiaryna.service.dto.RoleDTO;
import com.gmail.silina.katsiaryna.service.dto.UserDTO;
import com.gmail.silina.katsiaryna.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.gmail.silina.katsiaryna.service.constant.ServiceConstant.USER_NOT_FOUND_MSG;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {
    //private final RoleService roleService;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConvertService convertService;

    @Override
    public Long getPrincipalUserId() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }

    @Override
    public User getUserById(Long id) {
        var optionalOrder = userRepository.findById(id);
        return optionalOrder.orElse(null);
    }

    @Override
    public UserDTO getUserDTOById(Long id) {
        var user = getUserById(id);
        return convertService.getDTOFromObject(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUserDTOs() {
        var users = userRepository.findAll();
        return convertService.getDTOsFromObjectList(users, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUserDTOsByRoleName(RoleEnum roleName) {
        var users = userRepository.findAllByRoleName(roleName);
        return convertService.getDTOsFromObjectList(users, UserDTO.class);
    }

    @Override
    public void changeUserPasswordFrom(UserDTO userDTO) {
        var userId = userDTO.getId();
        var user = getUserById(userId);

        var password = userDTO.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
    }

    @Override
    public void changeUserRoleById(Long userId, RoleDTO roleDTO) {
        var user = getUserById(userId);
        var role = convertService.getObjectFromDTO(roleDTO, Role.class);
        user.setRole(role);
        userRepository.save(user);
    }

    @Override
    public void changeUserEnabledStatus(Long userId, boolean enabled) {
        var user = getUserById(userId);
        user.setEnabled(enabled);
        userRepository.save(user);
    }

    @Override
    @Transactional
    public void updateUserRoleAndEnabledStatusFrom(UserDTO userDTO) {
        var userId = userDTO.getId();
        var roleDTO = userDTO.getRole();
        var enabled = userDTO.isEnabled();

        changeUserRoleById(userId, roleDTO);
        changeUserEnabledStatus(userId, enabled);
    }

    @Override
    public void deleteUserById(Long id) {
        //TODO log
        try {
            userRepository.deleteById(id);
            log.info("");
        } catch (ServiceException e) {
            throw new ServiceException(String.format("User with id %s doesn't exist", id));
        }
    }

    @Override
    public void addClient(User user) {
        if (userRepository.findByUsername(user.getEmail()).isPresent()) {
            throw new ServiceException("There is user with that email address:" + user.getEmail());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        //TODO use roleService
        Role clientRole = roleRepository.findByName(RoleEnum.CLIENT);
        user.setRole(clientRole);
        userRepository.save(user);
    }

    @Override
    public void changeLastLogin(Long userId) {
        var user = getUserById(userId);
        user.setLastLogin(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, username)));
    }
}
