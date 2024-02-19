package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> listUsers();

    void delete(Long id);

    void save(User user);

    void saveAndFlush(User user);
    User findByUsername(String username);
    User getById(Long id);
    Optional<User> findById(Long id);
}
