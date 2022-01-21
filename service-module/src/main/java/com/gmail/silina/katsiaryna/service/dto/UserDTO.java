package com.gmail.silina.katsiaryna.service.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private RoleDTO role;
    private boolean enabled;
    private LocalDateTime lastLogin;
    private UserDetailsDTO userDetails;
}
