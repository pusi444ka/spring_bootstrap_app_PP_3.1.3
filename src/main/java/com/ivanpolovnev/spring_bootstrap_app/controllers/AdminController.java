package com.ivanpolovnev.spring_bootstrap_app.controllers;

import com.ivanpolovnev.spring_bootstrap_app.dao.RoleRepository;
import com.ivanpolovnev.spring_bootstrap_app.entity.Role;
import com.ivanpolovnev.spring_bootstrap_app.entity.User;
import com.ivanpolovnev.spring_bootstrap_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final RoleRepository roleRepository;

    @Autowired
    public AdminController(UserService userService, RoleRepository roleRepository) {
        this.userService = userService;
        this.roleRepository = roleRepository;
    }

    @GetMapping
    public String getAdminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("newUser") User user, @RequestParam("role") String roleName) {
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_" + roleName);

        if (optionalRole.isPresent()) {
            Role selectedRole = optionalRole.get();
            user.setRoles(Collections.singleton(selectedRole));
        } else {
            throw new RuntimeException("Role not found: " + roleName);
        }

        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String editUser(@RequestParam Long id,
                           @RequestParam String firstname,
                           @RequestParam String lastname,
                           @RequestParam int age,
                           @RequestParam String email,
                           @RequestParam(required = false) String password,
                           @RequestParam List<String> roles) {

        User existingUser = userService.findById(id);

        existingUser.setFirstname(firstname);
        existingUser.setLastname(lastname);
        existingUser.setAge(age);
        existingUser.setEmail(email);

        if (password != null && !password.isEmpty()) {
            existingUser.setPassword(password);
        }

        Set<Role> userRoles = roles.stream()
                .map(roleName -> roleRepository.findByName("ROLE_" + roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        existingUser.setRoles(userRoles);

        userService.updateUser(existingUser);

        return "redirect:/admin";
    }





    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}