package com.ivanpolovnev.spring_bootstrap_app.services;

import com.ivanpolovnev.spring_bootstrap_app.entity.User;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    void addUser(User user);
    void updateUser(User user);
    void deleteUser(Long id);
    User findById(Long id);
    User findByEmail(String email);
}

