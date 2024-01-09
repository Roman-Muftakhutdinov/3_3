package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.repositories.UserDao;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserDao userDao;
    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }
    @Autowired
    RoleRepository roleRepository;


    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    public List<User> listUsers() {
        return userDao.findAll();
    }


    public User show(Long id) {

        return userDao.findById(id).orElse(null);
    }


    public void delete(Long id) {

        userDao.deleteById(id);
    }


    public void save(User user) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        User userFromDB = userDao.findByUsername(user.getUsername());
        if (userFromDB == null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userDao.save(user);
        }

    }


    public User saveAndFlush(User user) {
        return userDao.saveAndFlush(user);
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User '%s' not found", username));
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
}
