package com.ivanpolovnev.spring_bootstrap_app.services;

import com.ivanpolovnev.spring_bootstrap_app.dao.RoleRepository;
import com.ivanpolovnev.spring_bootstrap_app.dao.UserRepository;
import com.ivanpolovnev.spring_bootstrap_app.entity.Role;
import com.ivanpolovnev.spring_bootstrap_app.entity.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional(readOnly = true)
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    @Override
    public void addUserWithRole(User user, String roleName) {
        Role role = roleRepository.findByName("ROLE_" + roleName)
                .orElseThrow(() -> new RuntimeException("Role not found: " + roleName));
        user.setRoles(Collections.singleton(role));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Transactional
    @Override
    public void updateUserData(Long id, String firstname, String lastname, int age, String email, String password, List<String> roles) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setFirstname(firstname);
        existingUser.setLastname(lastname);
        existingUser.setAge(age);
        existingUser.setEmail(email);

        if (password != null && !password.isEmpty()) {
            existingUser.setPassword(passwordEncoder.encode(password));
        }

        Set<Role> userRoles = roles.stream()
                .map(roleName -> roleRepository.findByName("ROLE_" + roleName)
                        .orElseThrow(() -> new RuntimeException("Role not found: " + roleName)))
                .collect(Collectors.toSet());

        existingUser.setRoles(userRoles);

        userRepository.save(existingUser);
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    @Transactional(readOnly = true)
    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }
}

