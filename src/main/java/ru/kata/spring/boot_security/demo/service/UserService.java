package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    List<User> listUsers();

    User show(Long id);

    void delete(Long id);

    void save(User user);

    void saveAndFlush(User user);
    User findByUsername(String username);
}
