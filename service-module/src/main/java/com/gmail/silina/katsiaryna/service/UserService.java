package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.repository.model.User;
import com.gmail.silina.katsiaryna.service.model.UserDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {

    //PageDTO<UserDTO> getAllByPagination(int pageNumber, int pageSize);

    List<UserDTO> getAll();

    Page<UserDTO> getAllByPage(int page, int limit);

    void addClient(UserDTO userDTO, Long roleId);

    void removeById(Long id);

    void changePasswordById(Long id);

    void changeRoleById(Long idUser, Long idRole);

    void addAndSendEmail(UserDTO user, Long idRole);

    void addAndSendEmail(UserDTO user);

    void addClient(User user);
}
