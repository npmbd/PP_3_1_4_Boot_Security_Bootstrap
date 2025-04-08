package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public Set<Role> getRolesFromUserDto(UserDto userDto) {
        return userDto.getRoles().stream().map(this::getOrCreateRole).collect(Collectors.toSet());
    }

    @Override
    public Role getOrCreateRole(String roleName) {
        return roleRepository.findByName(roleName).orElse(new Role(roleName));
    }
}
