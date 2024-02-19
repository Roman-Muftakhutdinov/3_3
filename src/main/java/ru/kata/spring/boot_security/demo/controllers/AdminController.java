package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.exception_handling.NoSuchUserException;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import java.util.List;


@RestController
@CrossOrigin
@RequestMapping("/api/admin/")
public class AdminController {

    private final RoleService roleService;
    private final UserService userService;

    public AdminController(RoleService roleService, UserService userService) {
        this.roleService = roleService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<User>> listUsers() {
        List<User> allUsers = userService.listUsers();

        return allUsers != null && !allUsers.isEmpty()
                ? new ResponseEntity<>(allUsers, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/getRoles")
    public ResponseEntity<List<Role>> getRoles() {
        List<Role> allRoles = roleService.findAll();

        return allRoles != null && !allRoles.isEmpty()
                ? new ResponseEntity<>(allRoles, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<User> add(@RequestBody User user)  {
        userService.save(user);

        return user != null
                ? new ResponseEntity<>(user,HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable("id") Long id, @RequestBody User user) {
        user.setId(id);
        BCryptPasswordEncoder passwordEncoder2 = new BCryptPasswordEncoder();
        user.setPassword(passwordEncoder2.encode(user.getPassword()));

        userService.saveAndFlush(user);

        return user != null
                ? new ResponseEntity<>(user,HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){

         userService.delete(id);

         String s =  "User with id:" + id + " was deleted.";
        return userService.getById(id) == null
                ? new ResponseEntity<>(s, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }
}
