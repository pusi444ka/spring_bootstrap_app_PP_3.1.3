package com.ivanpolovnev.spring_bootstrap_app.services;

import com.ivanpolovnev.spring_bootstrap_app.entity.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    void addUserWithRole(User user, String roleName);

    void updateUserData(Long id, String firstname, String lastname, int age, String email, String password, List<String> roles);

    void deleteUser(Long id);

    User findByEmail(String email);

    User findById(Long id);
}


