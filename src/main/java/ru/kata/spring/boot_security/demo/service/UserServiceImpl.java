package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getById(Long id) {
        return userRepository.getById(id);
    }

    public List<User> listUsers() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void delete(Long id) {

        userRepository.deleteById(id);
    }

    @Transactional
    public void save(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        }

    }
    @Transactional
    public void saveAndFlush(User user) {

        userRepository.saveAndFlush(user);
    }
}
