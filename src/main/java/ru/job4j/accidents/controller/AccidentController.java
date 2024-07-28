package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.Accident;
import ru.job4j.accidents.service.AccidentService;
import ru.job4j.accidents.service.AccidentTypeService;

@Controller
@RequestMapping("/accidents")
@AllArgsConstructor
public class AccidentController {
    private final AccidentService simpleAccidentService;

    private final AccidentTypeService simpleAccidentTypeService;

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("accidents", simpleAccidentService.findAll());
        return "accidents/list";
    }

    @GetMapping("/create")
    public String viewCreateAccident(Model model) {
        model.addAttribute("types", simpleAccidentTypeService.findAll());
        return "accidents/create";
    }

    @PostMapping("/create")
    public String save(@ModelAttribute Accident accident) {
        simpleAccidentService.create(accident);
        return "redirect:/accidents";
    }

    @GetMapping("/update")
    public String viewEditAccident(Model model, @RequestParam("id") int accidentId) {
        var accidentOptional = simpleAccidentService.findById(accidentId);
        if (accidentOptional.isEmpty()) {
            model.addAttribute("message", "Заявка с указанным ID не найдена.");
            return "errors/404";
        }
        model.addAttribute("accident", accidentOptional.get());
        model.addAttribute("types", simpleAccidentTypeService.findAll());
        return "accidents/edit";
    }

    @PostMapping("/update")
    public String edit(@ModelAttribute Accident accident, Model model) {
        var isUpdated = simpleAccidentService.update(accident);
        if (!isUpdated) {
            model.addAttribute("message", "Заявка с указанным ID не найдена");
            return "errors/404";
        }
        return "redirect:/accidents";
    }
}
