package com.vtlong.my_spring_boot_project;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.vtlong.my_spring_boot_project.model.RoleType;
import com.vtlong.my_spring_boot_project.model.User;
import com.vtlong.my_spring_boot_project.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        log.info("Starting data initialization...");
        
        if (userRepository.existsByEmail("admin@example.com")) {
            log.info("Admin user already exists, skipping initialization");
            return;
        }

        User adminUser = User.builder()
                .email("admin@example.com")
                .password(passwordEncoder.encode("12345678"))
                .firstName("Long")
                .lastName("Viet")
                .roles(new HashSet<>(Arrays.asList(RoleType.values())))
                .build();

        try {
            User savedUser = userRepository.save(adminUser);
            log.info("Successfully created admin user with ID: {}", savedUser.getId());
            log.info("Admin user created with roles: {}", savedUser.getRoles());
        } catch (Exception e) {
            log.error("Failed to create admin user: {}", e.getMessage());
        }
        
        log.info("Data initialization completed");
    }
}
