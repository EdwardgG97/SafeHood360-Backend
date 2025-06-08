package com.safehood.config;

import com.safehood.model.User;
import com.safehood.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository) {
        return args -> {
            // Crear usuario administrador por defecto si no existe
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User(
                    "admin",
                    "admin123",
                    "admin@safehood360.com"
                );
                admin.setRole(User.Role.ADMIN);
                userRepository.save(admin);
            }
        };
    }
}
