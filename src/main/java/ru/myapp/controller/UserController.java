package ru.myapp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.myapp.model.entity.UserDetails;
import ru.myapp.service.db.UserService;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/details/{userName}")
    public UserDetails getUserDetailsByUserName(@PathVariable("userName") String userName) {
        return userService.getUserDetailsByUserName(userName);
    }

    @GetMapping("/emails")
    public List<String> getClientsEmails() {
        return userService.getClientsEmails();
    }
}
