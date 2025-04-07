//package ru.kata.spring.boot_security.demo.controller;
//
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.ModelAttribute;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import ru.kata.spring.boot_security.demo.dto.UserDto;
//import ru.kata.spring.boot_security.demo.service.UserService;
//
//@Controller
//@RequestMapping("/registration")
//public class RegistrationController {
//
//    private final UserService userService;
//
//    public RegistrationController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @GetMapping
//    public String showRegistrationForm(Model model) {
//        UserDto userDto = new UserDto();
//        model.addAttribute("userDto", userDto);
//        return "registration";
//    }
//
//    @PostMapping
//    public String registerAccount(@ModelAttribute("userDto") UserDto userDto) {
//        userService.saveOrUpdate(userDto);
//        return "redirect:/login";
//    }
//}
