package ru.kata.spring.boot_security.demo.dto;

import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Mapper {

    public UserDto toDto(User user) {
        long id = user.getId();
        String username = user.getUsername();
        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String phoneNumber = user.getPhoneNumber();
        String password = user.getPassword();
        Set<String> roles = user.getRoles().stream().map(Role::toString).collect(Collectors.toSet());
        return new UserDto(id, username, firstName, lastName, email, phoneNumber, password, roles);
    }

    public User toUser(UserDto userDto) {
        return new User(userDto.getId(),
                userDto.getUsername(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail(),
                userDto.getPhoneNumber(),
                userDto.getPassword(),
                new HashSet<>());
    }

    public User toUser(UserDto userDto, User existingUser) {
        existingUser.setUsername(userDto.getUsername());
        existingUser.setFirstName(userDto.getFirstName());
        existingUser.setLastName(userDto.getLastName());
        existingUser.setEmail(userDto.getEmail());
        existingUser.setPhoneNumber(userDto.getPhoneNumber());
        return existingUser;
    }
}
