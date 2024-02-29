package ru.kata.spring.boot_security.demo.configs;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
public class InitDB implements ApplicationListener<ContextRefreshedEvent> {

    private RoleService roleService;
    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;

    public InitDB(RoleService roleService, UserServiceImpl userService, PasswordEncoder passwordEncoder) {
        this.roleService = roleService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Role userRole = new Role();
        userRole.setName("ROLE_USER");
        roleService.save(userRole);

        Role adminRole = new Role();
        adminRole.setName("ROLE_ADMIN");
        roleService.save(adminRole);

        Collection<Role> userRoles = new HashSet<>(Arrays.asList(userRole));
        Collection<Role> adminRoles = new HashSet<>(Arrays.asList(adminRole));

        User admin = new User ();
        admin.setUsername("admin");
        admin.setSurname("adminov");
        admin.setAge(33);
        admin.setPassword(passwordEncoder.encode("200"));
        admin.setRoles(adminRoles);

        User user = new User();
        user.setUsername("user");
        user.setSurname("userov");
        user.setAge(35);
        user.setPassword(passwordEncoder.encode("user"));
        user.setRoles(userRoles);

        userService.save(admin);
        userService.save(user);

    }
}
