package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.RoleRepository;
import com.gmail.silina.katsiaryna.repository.UserDetailsRepository;
import com.gmail.silina.katsiaryna.repository.UserRepository;
import com.gmail.silina.katsiaryna.repository.model.Role;
import com.gmail.silina.katsiaryna.repository.model.RoleEnum;
import com.gmail.silina.katsiaryna.repository.model.User;
import com.gmail.silina.katsiaryna.service.UserService;
import com.gmail.silina.katsiaryna.service.dto.UserDTO;
import com.gmail.silina.katsiaryna.service.exception.ServiceException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private UserRepository userRepository;
    private UserDetailsRepository userDetailsRepository;
    private ModelMapper modelMapper;

    @Override
    public List<UserDTO> getAll() {
        var users = userRepository.findAll();
        return users.stream()
                .map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserDTO> getAllByPage(int page, int limit) {
        var pageable = PageRequest.of(page, limit);
        var users = userRepository.findAll(pageable);
        return new PageImpl<>(users.getContent().stream().map(el -> modelMapper.map(el, UserDTO.class)).collect(Collectors.toList()), pageable, users.getTotalElements());
    }

    @Override
    public void addClient(UserDTO userDTO, Long roleId) {

    }

    @Override
    public void removeById(Long id) {
        var user = userRepository.findById(id);
        if (user != null) {
            //userRepository.remove(user);
        } else {
            //TODO Why is't here catch section ?
            throw new ServiceException(String.format("User with id %s doesn't exist", id));
        }
    }

    @Override
    public void changePasswordById(Long id) {

    }

    @Override
    public void changeRoleById(Long idUser, Long idRole) {

    }

    @Override
    public void addAndSendEmail(UserDTO user, Long idRole) {

    }

    @Override
    public void addAndSendEmail(UserDTO user) {

    }

    @Override
    public void addClient(User user) {
        Role clientRole = roleRepository.findByName(RoleEnum.CLIENT);
        user.setRole(clientRole);
        userRepository.save(user);
    }
}
