package com.ivanpolovnev.spring_bootstrap_app.service;


import com.ivanpolovnev.spring_bootstrap_app.entity.User;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
    User findByEmail(String email);
}
