package com.ivanpolovnev.spring_bootstrap_app.controllers;


import com.ivanpolovnev.spring_bootstrap_app.entity.Role;
import com.ivanpolovnev.spring_bootstrap_app.entity.User;
import com.ivanpolovnev.spring_bootstrap_app.service.RoleService;
import com.ivanpolovnev.spring_bootstrap_app.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String getAdminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        model.addAttribute("allRoles", roleService.getAllRoles());
        return "admin";
    }

    @PostMapping("/add")
    public String addUser(
            @ModelAttribute("newUser") User user,
            @RequestParam(value = "roles", required = false) List<Long> roleIds
    ) {
        Set<Role> roles = roleIds != null ? roleService.findByIds(roleIds) : new HashSet<>();
        user.setRoles(roles);
        userService.addUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/edit")
    public String editUser(
            @ModelAttribute("user") User user,
            @RequestParam(value = "roles", required = false) List<Long> roleIds
    ) {
        Set<Role> roles = roleIds != null ? roleService.findByIds(roleIds) : new HashSet<>();
        user.setRoles(roles);
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}