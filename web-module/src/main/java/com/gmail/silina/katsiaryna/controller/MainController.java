package com.gmail.silina.katsiaryna.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping
    public String welcomePage(Model model) {
        model.addAttribute("title", "Car rental");
        model.addAttribute("message", "This is welcome page!");
        return "home";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
}
