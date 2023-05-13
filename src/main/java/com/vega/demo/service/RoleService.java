package com.vega.demo.service;

import com.vega.demo.domain.Role;
import com.vega.demo.repository.RoleRepository;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }
 /*   public Role findByName(String name) {
        return roleRepository.findByName(name);
    }*/
}