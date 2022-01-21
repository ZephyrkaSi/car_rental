package com.gmail.silina.katsiaryna.service;

import com.gmail.silina.katsiaryna.repository.model.UserDetails;
import com.gmail.silina.katsiaryna.service.dto.UserDetailsDTO;

public interface UserDetailsService {

    UserDetails getUserDetailsById(Long id);

    UserDetailsDTO getUserDetailsDTOById(Long id);

    void add(UserDetails userDetails);

    void changeUserDetailsFrom(UserDetailsDTO userDetailsDTO);

}
