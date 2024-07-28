package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.job4j.accidents.model.User;

@Controller
public class IndexController {
    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        var user = new User(1, "Pavel Yurchenko", "login", "password");
        model.addAttribute("user", user);
        return "index";
    }
}