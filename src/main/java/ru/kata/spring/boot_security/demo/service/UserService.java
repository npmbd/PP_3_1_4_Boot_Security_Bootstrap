package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.dto.UserDto;

public interface UserService extends UserDetailsService {
    void saveOrUpdate(UserDto registrationDto);
}
