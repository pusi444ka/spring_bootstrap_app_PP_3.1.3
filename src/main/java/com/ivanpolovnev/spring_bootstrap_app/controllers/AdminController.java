package com.ivanpolovnev.spring_bootstrap_app.controllers;

import com.ivanpolovnev.spring_bootstrap_app.dao.RoleRepository;
import com.ivanpolovnev.spring_bootstrap_app.entity.Role;
import com.ivanpolovnev.spring_bootstrap_app.entity.User;
import com.ivanpolovnev.spring_bootstrap_app.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getAdminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(@ModelAttribute("newUser") User user, @RequestParam("role") String roleName) {
        userService.addUserWithRole(user, roleName);
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

        userService.updateUserData(id, firstname, lastname, age, email, password, roles);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
