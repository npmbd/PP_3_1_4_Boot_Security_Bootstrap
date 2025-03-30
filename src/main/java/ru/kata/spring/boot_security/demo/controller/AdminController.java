package ru.kata.spring.boot_security.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.dto.Mapper;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.service.UserService;

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
    public String adminPage(ModelMap model) {
        model.addAttribute("users", userService.getUserDtos());
        return "adminPage";
    }

    @GetMapping("/addNewUser")
    public String addUser(ModelMap model) {
        UserDto userDto = new UserDto();
        model.addAttribute("userDto", userDto);
        return "userInfo";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") UserDto userDto) {
        userService.saveOrUpdate(userDto);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String updateUserForm(@RequestParam("userId") long id, ModelMap model) {
        UserDto userDto = mapper.toDto(userService.getUserById(id).get());
        model.addAttribute("userDto", userDto);
        return "userInfo";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("userId") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }
}
