package ru.job4j.accidents.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping({"/", "/index"})
    public String getIndex(Model model) {
        record User(int id, String name) {
        }
        var user = new User(1, "Pavel Yurchenko");
        model.addAttribute("user", user);
        return "index";
    }
}