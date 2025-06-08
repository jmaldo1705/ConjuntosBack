package com.conjuntos.conjuntosback.config;

import com.conjuntos.conjuntosback.auth.model.User;
import com.conjuntos.conjuntosback.auth.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // Check if we already have users
            if (userRepository.count() == 0) {
                // Create admin user
                User admin = new User(
                        "admin",
                        "admin@conjuntos.com",
                        passwordEncoder.encode("admin123")
                );
                admin.setFullName("Administrador");
                admin.setApartmentNumber("N/A");
                admin.setPhoneNumber("123456789");
                
                Set<String> adminRoles = new HashSet<>();
                adminRoles.add("ADMIN");
                admin.setRoles(adminRoles);
                
                userRepository.save(admin);
                
                // Create resident user
                User resident = new User(
                        "resident",
                        "resident@conjuntos.com",
                        passwordEncoder.encode("resident123")
                );
                resident.setFullName("Residente Ejemplo");
                resident.setApartmentNumber("101");
                resident.setPhoneNumber("987654321");
                
                Set<String> residentRoles = new HashSet<>();
                residentRoles.add("USER");
                resident.setRoles(residentRoles);
                
                userRepository.save(resident);
                
                System.out.println("Sample users created successfully!");
            }
        };
    }
}