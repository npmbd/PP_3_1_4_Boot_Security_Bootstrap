package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.entities.Role;

import java.util.Set;

public interface RoleService {

    Set<Role> getRolesFromUserDto(UserDto userDto);

    Role getOrCreateRole(String roleNames);
}
