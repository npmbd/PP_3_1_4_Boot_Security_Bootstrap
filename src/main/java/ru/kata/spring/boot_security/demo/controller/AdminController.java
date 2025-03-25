package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping
    public String adminPage(ModelMap model) {
        model.addAttribute("users", userRepository.findAll());
        return "adminPage";
    }

    @GetMapping("/addNewUser")
    public String addUser(ModelMap model) {
        User user = new User();
        Set<String> roles = new HashSet<>();
        model.addAttribute("user", user);
        model.addAttribute("roles", roles);
        return "userInfo";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, @RequestParam(value = "selectedRoles", required = false) Set<String> roleNames) {
        if (roleNames != null && !roleNames.isEmpty()) {
            Set<Role> roles = roleNames.stream()
                    .map(roleName -> roleRepository.findByName(roleName)
                            .orElse(new Role(roleName)))
                    .collect(Collectors.toSet());
            user.setRoles(roles);
        } else {
            user.setRoles(new HashSet<>());
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String updateUserForm(@RequestParam("userId") long id, ModelMap model) {
        User user = userRepository.getReferenceById(id);
        model.addAttribute("user", user);
        Set<String> roleNames = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        model.addAttribute("roles", roleNames);
        return "userInfo";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam("userId") long id) {
        userRepository.deleteById(id);
        return "redirect:/admin";
    }
}
