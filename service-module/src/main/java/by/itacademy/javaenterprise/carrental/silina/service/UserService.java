package by.itacademy.javaenterprise.carrental.silina.service;

import by.itacademy.javaenterprise.carrental.silina.repository.model.RoleEnum;
import by.itacademy.javaenterprise.carrental.silina.repository.model.User;
import by.itacademy.javaenterprise.carrental.silina.service.dto.RoleDTO;
import by.itacademy.javaenterprise.carrental.silina.service.dto.UserDTO;

import java.util.List;

public interface UserService {

    Long getPrincipalUserId();

    User getUserById(Long id);

    UserDTO getUserDTOById(Long id);

    List<UserDTO> getAllUserDTOs();

    List<UserDTO> getAllUserDTOsByRoleName(RoleEnum roleName);

    void changeUserRoleById(Long userId, RoleDTO roleDTO);

    void changeUserEnabledStatus(Long userId, boolean enabled);

    void updateUserRoleAndEnabledStatusFrom(UserDTO userDTO);

    void deleteUserById(Long id);

    void changeUserPasswordFrom(UserDTO userDTO);

    void addClient(User user);

    void changeLastLogin(Long userId);
}
