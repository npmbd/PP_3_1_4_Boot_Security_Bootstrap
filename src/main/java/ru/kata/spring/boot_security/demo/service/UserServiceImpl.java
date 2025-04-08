package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.dto.Mapper;
import ru.kata.spring.boot_security.demo.dto.UserDto;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final Mapper mapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService, Mapper mapper) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
        this.mapper = mapper;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    @Transactional
    @Override
    public void saveOrUpdate(UserDto userDto) {

        User existingUser = userRepository.findById(userDto.getId()).orElse(null);

        if (existingUser == null) {
            User user = mapper.toUser(userDto);
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
            user.setRoles(roleService.getRolesFromUserDto(userDto));
            userRepository.save(user);
        } else {
            existingUser = mapper.toUser(userDto, existingUser);
            if (!userDto.getPassword().isEmpty()
                    && !passwordEncoder.matches(userDto.getPassword(), existingUser.getPassword())) {
                existingUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
            }
            existingUser.setRoles(roleService.getRolesFromUserDto(userDto));
            userRepository.save(existingUser);
        }
    }

    @Override
    public Collection<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDto> getUserDtos() {
        return userRepository.findAll()
                .stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto getUserDtoByUsername(String username) {
        return userRepository.findByUsername(username).map(mapper::toDto).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
    }

    @Override
    public void createUsers() {

        User user = new User();
        user.setUsername("user");
        user.setFirstName("user");
        user.setLastName("user");
        user.setEmail("user@user.com");
        user.setPhoneNumber("123456789");
        user.setPassword(passwordEncoder.encode("123"));
        user.setRoles(Set.of(roleService.getOrCreateRole("ROLE_USER")));
        userRepository.save(user);

        User admin = new User();
        admin.setUsername("admin");
        admin.setFirstName("admin");
        admin.setLastName("admin");
        admin.setEmail("admin@admin.com");
        admin.setPhoneNumber("123456789");
        admin.setPassword(passwordEncoder.encode("123"));
        admin.setRoles(Set.of(roleService.getOrCreateRole("ROLE_ADMIN")));
        userRepository.save(admin);

        User adminAndUser = new User();
        adminAndUser.setUsername("admin-user");
        adminAndUser.setFirstName("admin-user");
        adminAndUser.setLastName("admin-user");
        adminAndUser.setEmail("admin-user@admin.com");
        adminAndUser.setPhoneNumber("123456789");
        adminAndUser.setPassword(passwordEncoder.encode("123"));
        adminAndUser.setRoles(Set.of(roleService.getOrCreateRole("ROLE_ADMIN"), roleService.getOrCreateRole("ROLE_USER")));
        userRepository.save(adminAndUser);
    }
}
