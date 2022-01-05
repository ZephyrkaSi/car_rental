package com.gmail.silina.katsiaryna.service.model;

import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String email;
    private String role;
    private boolean enabled;
    private Date lastLogin;
}
