package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.entities.Role;
import ru.kata.spring.boot_security.demo.entities.User;
import ru.kata.spring.boot_security.demo.repositories.RoleRepository;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;


@Controller
public class UsersController {

    @Autowired
    private RoleRepository roleRepository;
    private final UserService userService;

    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String listUsers(Model model) {
        model.addAttribute("users", userService.listUsers());
        return "list";
    }

    @GetMapping("/admin/{id}")
    public String show(@PathVariable("id") Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "show";
//        return "redirect:/user";
    }
    @GetMapping("/user")
   public String pageUser(Principal principal, Model model) {
        User user = userService.findByUsername(principal.getName());
        model.addAttribute("user", userService.show(user.getId()));
        return "show";
    }
    @GetMapping("/admin/new")
    public String newPerson(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        List<Role> roles =  roleRepository.findAll();//(List<Role>)
        model.addAttribute("allRoles", roles);
        return "new";
    }

    @PostMapping("/admin")
    public String add(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "/new";
        userService.save(user);
        return "redirect:/admin";
    }

    @GetMapping("/admin/edit")
    public String edit (@RequestParam Long id, Model model) {
        model.addAttribute("user", userService.show(id));
        return "edit";
    }

    @PostMapping("/admin/{id}")
    public String update( @ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "edit";
        userService.saveAndFlush(user);
        return  "redirect:/admin";
    }

    @DeleteMapping("/admin/{id}")
    public String delete(@PathVariable("id") Long id){
        userService.delete(id);
        return "redirect:/admin";
    }
}
