package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
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

    @GetMapping("/create")
    public String viewCreateAccident() {
        return "accidents/create";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute Accident accident) {
        simpleAccidentService.create(accident);
        return "redirect:/accidents";
    }

    @GetMapping("/{accidentId}")
    public String viewEditAccident(Model model, @PathVariable int accidentId) {
        var accidentOptional = simpleAccidentService.findById(accidentId);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Заявка с указанным ID не найдена.");
            return "errors/404";
        }
        model.addAttribute("accident", simpleAccidentService.findById(accidentId).get());
        return "accidents/edit";
    }

    @PostMapping("/{id}/update")
    public String edit(@ModelAttribute Accident accident, Model model) {
        var isUpdated = simpleAccidentService.update(accident);
        if (!isUpdated) {
            model.addAttribute("message", "Заявка с указанным ID не найдена");
            return "errors/404";
        }
        return "redirect:/accidents";
    }
}
