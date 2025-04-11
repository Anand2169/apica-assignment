package com.user.config.security.services;

import java.util.Arrays;

import org.springframework.stereotype.Component;

import com.user.enums.RoleType;
import com.user.model.Role;
import com.user.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

@Component
public class RoleInitializer {
    private final RoleRepository roleRepository;

    public RoleInitializer(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    public void init() {
        Arrays.stream(RoleType.values()).forEach(roleType -> {
            if (!roleRepository.existsByName(roleType)) {
                roleRepository.save(new Role(roleType));
            }
        });
    }
}	