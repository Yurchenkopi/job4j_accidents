package ru.job4j.accidents.controller;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.repository.AuthoritySpringDataRepository;
import ru.job4j.accidents.service.SpringDataUserService;

@Controller
@AllArgsConstructor
public class RegController {

    private final PasswordEncoder encoder;
    private final SpringDataUserService springDataUserService;
    private final AuthoritySpringDataRepository authorities;

    @PostMapping("/reg")
    public String regSave(@ModelAttribute User user, Model model) {
        user.setEnabled(true);
        user.setPassword(encoder.encode(user.getPassword()));
        user.setAuthority(authorities.findByAuthority("ROLE_USER"));
        var savedUser = springDataUserService.save(user);
        if (savedUser.isEmpty()) {
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
