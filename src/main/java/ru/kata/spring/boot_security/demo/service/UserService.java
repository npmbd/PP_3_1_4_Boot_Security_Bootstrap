package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    void saveOrUpdate(UserDto registrationDto);

    Collection<User> findAll();

    Optional<User> getUserById(Long id);

    void deleteUserById(Long id);

    User findUserByUsername(String username);

    List<UserDto> getUserDtos();

    UserDto getUserDtoByUsername(String username);
}
