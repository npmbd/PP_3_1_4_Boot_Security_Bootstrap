package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
public class CommonController {

    private final UserService userService;

    public CommonController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "registration";
    }

    @PostMapping("/registration")
    public String registerAccount(@ModelAttribute("userDto") UserDto userDto) {
        userService.saveOrUpdate(userDto);
        return "redirect:/login";
    }
}
