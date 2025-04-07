package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.Mapper;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final Mapper mapper;

    public AdminController(UserService userService, Mapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
    }

    @GetMapping
    public String adminPage(ModelMap model, Principal principal) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        model.addAttribute("users", userService.getUserDtos());
        model.addAttribute("principal", principal);
        model.addAttribute("currentUser", userService.getUserDtoByUsername(principal.getName()));
        return "adminPage";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") UserDto userDto) {
        userService.saveOrUpdate(userDto);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String updateUserForm(@RequestParam("userId") long id, ModelMap model, Principal principal) {
        UserDto userDto = mapper.toDto(userService.getUserById(id).get());
        model.addAttribute("userToEdit", userDto);
        model.addAttribute("users", userService.getUserDtos());
        model.addAttribute("roles", userService.findUserByUsername(principal.getName()).getRoles());
        model.addAttribute("principal", principal);
        return "adminPage";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("userId") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
