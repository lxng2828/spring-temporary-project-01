package com.vtlong.my_spring_boot_project;

import java.util.Arrays;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.vtlong.my_spring_boot_project.exception.AppException;
import com.vtlong.my_spring_boot_project.exception.ExceptionCode;
import com.vtlong.my_spring_boot_project.model.RoleType;
import com.vtlong.my_spring_boot_project.model.User;
import com.vtlong.my_spring_boot_project.repository.UserRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.existsByEmail("admin@example.com")) {
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
            userRepository.save(adminUser);
        } catch (Exception e) {
            throw new AppException("Failed to create admin user", ExceptionCode.INTERNAL_SERVER_ERROR);
        }
    }
}
