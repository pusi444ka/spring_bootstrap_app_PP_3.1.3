package com.ivanpolovnev.spring_bootstrap_app.init;

import com.ivanpolovnev.spring_bootstrap_app.dao.RoleRepository;
import com.ivanpolovnev.spring_bootstrap_app.dao.UserRepository;
import com.ivanpolovnev.spring_bootstrap_app.entity.Role;
import com.ivanpolovnev.spring_bootstrap_app.entity.User;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class InitAddAdmin {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public InitAddAdmin(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void init() {
        Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_ADMIN")));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseGet(() -> roleRepository.save(new Role("ROLE_USER")));

        if (userRepository.findByEmail("admin@mail.com").isEmpty()) {
            User user = new User();
            user.setEmail("admin@mail.com");
            user.setFirstName("Admin");
            user.setLastName("Admin");
            user.setAge(99);
            user.setPassword(passwordEncoder.encode("admin"));
            user.setRoles(Set.of(adminRole, userRole));
            userRepository.save(user);
        }
    }
}

