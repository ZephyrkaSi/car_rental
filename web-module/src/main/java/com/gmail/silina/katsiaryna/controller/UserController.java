package com.gmail.silina.katsiaryna.controller;

import com.gmail.silina.katsiaryna.repository.model.User;
import com.gmail.silina.katsiaryna.repository.model.UserDetails;
import com.gmail.silina.katsiaryna.service.UserDetailsService;
import com.gmail.silina.katsiaryna.service.UserService;
import com.gmail.silina.katsiaryna.service.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Base64;

import static com.gmail.silina.katsiaryna.constant.HandlerConstants.USERS_URL;

@Controller
@CrossOrigin
@RequestMapping(USERS_URL)
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final UserDetailsService userDetailsService;

    @PostMapping("/login")
    @ResponseBody
    public boolean login(@RequestBody User user) {
        return user.getUsername().equals("user") && user.getPassword().equals("password");
    }

    @RequestMapping("/user")
    public Principal user(HttpServletRequest request) {
        String authToken = request.getHeader("Authorization").substring("Basic".length()).trim();
        return () -> new String(Base64.getDecoder().decode(authToken)).split(":")[0];
    }


    @GetMapping("/all")
    public String getAllUsers() {
        return "users";
    }

    @GetMapping("/api/all")
    @ResponseBody
    public Page<UserDTO> getAllUsers(@RequestParam(value = "page", required = false/*, defaultValue = "0"*/) Integer page,
                                     @RequestParam(value = "limit", required = false/*, defaultValue = "10"*/) Integer limit) {
        return userService.getAllByPage(page, limit);
    }

    @GetMapping("/add")
    public String redirectToAddUser(@ModelAttribute("user") User user,
                                    @ModelAttribute("userDetails") UserDetails userDetails) {
        return "user_registration";
    }

    @PostMapping("/registration")
    public String addUser(@RequestBody @ModelAttribute("user") @Valid User user,
                          BindingResult resultUser,
                          @RequestBody @ModelAttribute("userDetails") @Valid UserDetails userDetails,
                          BindingResult resultUserDetails) {
        if (resultUser.hasErrors() || resultUserDetails.hasErrors()) {
            return "user_registration";
        } else {
            userDetailsService.add(userDetails);
            user.setUserDetails(userDetails);
            userService.addClient(user);
            return "user_success_registration";
        }
    }

    @PostMapping("/delete")
    public String deleteItems(@RequestParam(value = "selected", required = false) Long[] ids) {
        if (ids != null) {
            for (Long id : ids) {
                userService.removeById(id);
            }
        }
        return "redirect:" + USERS_URL;
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam(value = "userIdForChangePass", required = false) Long id) {
        userService.changePasswordById(id);
        return "redirect:" + USERS_URL;
    }

    @PostMapping("/change-role")
    public String changeRole(@RequestParam(value = "id") Long idUser,
                             @RequestParam(value = "roleId") Long roleId) {
        userService.changeRoleById(idUser, roleId);
        return "redirect:" + USERS_URL;
    }
}
