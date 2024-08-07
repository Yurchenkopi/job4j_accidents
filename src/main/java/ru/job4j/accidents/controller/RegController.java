package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthoritySpringDataRepository;
import ru.job4j.accidents.repository.UserSpringDataRepository;
import ru.job4j.accidents.service.SpringDataAccidentService;

@Controller
@AllArgsConstructor
public class RegController {

    private final PasswordEncoder encoder;
    private final UserSpringDataRepository users;
    private final AuthoritySpringDataRepository authorities;

    private static final Logger LOG = LoggerFactory.getLogger(RegController.class.getName());

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        try {
            users.save(user);
        } catch (Exception e) {
            LOG.error("Error occurred while saving user:  " + e.getMessage());
            model.addAttribute("errorMessage", "Пользователь с таким username уже зарегистрирован.");
            return "register";
        }

        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "register";
    }
}
