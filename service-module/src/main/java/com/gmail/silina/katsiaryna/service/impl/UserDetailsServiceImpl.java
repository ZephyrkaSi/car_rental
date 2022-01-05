package com.gmail.silina.katsiaryna.service.impl;

import com.gmail.silina.katsiaryna.repository.UserDetailsRepository;
import com.gmail.silina.katsiaryna.repository.model.UserDetails;
import com.gmail.silina.katsiaryna.service.UserDetailsService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserDetailsRepository userDetailsRepository;

    @Override
    public void add(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }
}
