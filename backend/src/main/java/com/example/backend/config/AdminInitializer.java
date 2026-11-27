package com.example.backend.config;

import com.example.backend.entities.Role;
import com.example.backend.entities.User;
import com.example.backend.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Slf4j
@Component
@RequiredArgsConstructor
public class AdminInitializer implements CommandLineRunner {

    @Value("${admin.email:}")
    private String adminEmail;

    @Value("${admin.password:}")
    private String adminPassword;

    @Value("${admin.firstName:}")
    private String adminFirstName;

    @Value("${admin.lastName:}")
    private String adminLastName;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {

        if (adminEmail == null || adminEmail.isBlank() || adminPassword==null || adminPassword.isBlank()){
            log.warn("Admin initialization skipped: email or password is missing.");
            return;
        }
        adminEmail = adminEmail.trim().toLowerCase(Locale.ROOT);
        adminPassword = adminPassword.trim().toLowerCase();
        if(userRepository.existsByEmail(adminEmail)){
            log.warn("Admin initialization skipped: email already exists.");
            return;
        }
        User admin = new User();
        admin.setEmail(adminEmail);
        admin.setPasswordHash(passwordEncoder.encode(adminPassword));
        admin.setRole(Role.ADMIN);
        admin.setFirstName(adminFirstName);
        admin.setLastName(adminLastName);

        try {
            userRepository.save(admin);
        }catch (DataIntegrityViolationException e){
            log.warn("Admin initialization skipped: ",e);
            return;
        }

        log.info("Admin account successfully initialized.");

    }
}
