package com.gmail.silina.katsiaryna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.gmail.silina.katsiaryna.constant.HandlerConstants.MAIN_URL;

@Controller
@RequestMapping(MAIN_URL)
public class MainController {

    @GetMapping
    public String welcomePage(Model model) {
        model.addAttribute("title", "Car rental");
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
