package com.ivanpolovnev.spring_bootstrap_app.service;

import com.ivanpolovnev.spring_bootstrap_app.dao.RoleRepository;
import com.ivanpolovnev.spring_bootstrap_app.entity.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Transactional(readOnly = true)
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Set<Role> findByIds(List<Long> ids) {
        return new HashSet<>(roleRepository.findAllById(ids));
    }
}
