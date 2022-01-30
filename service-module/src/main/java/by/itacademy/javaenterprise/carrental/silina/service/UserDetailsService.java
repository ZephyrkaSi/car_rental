package by.itacademy.javaenterprise.carrental.silina.service;

import by.itacademy.javaenterprise.carrental.silina.repository.model.UserDetails;
import by.itacademy.javaenterprise.carrental.silina.service.dto.UserDetailsDTO;

public interface UserDetailsService {

    UserDetails getUserDetailsById(Long id);

    UserDetailsDTO getUserDetailsDTOById(Long id);

    void add(UserDetails userDetails);

    void changeUserDetailsFrom(UserDetailsDTO userDetailsDTO);
}
