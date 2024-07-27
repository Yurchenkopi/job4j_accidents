package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.accidents.service.AccidentService;

@Controller
@RequestMapping("/accidents")
@AllArgsConstructor
public class AccidentController {
    private final AccidentService simpleAccidentService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("accidents", simpleAccidentService.findAll());
        return "accidents/list";
    }
}
