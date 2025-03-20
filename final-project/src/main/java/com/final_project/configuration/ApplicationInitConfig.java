package com.final_project.configuration;

import com.final_project.entity.Role;
import com.final_project.entity.User;
import com.final_project.repository.RoleRepository;
import com.final_project.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;

@Slf4j
@Configuration
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    RoleRepository roleRepository;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            if (userRepository.findByUsername("admin").isEmpty()) {
                Role adminRole = roleRepository.save(Role.builder()
                        .name("ADMIN")
                        .description("Admin role")
                        .build());
                var roles = new HashSet<Role>();
                roles.add(adminRole);
                User user = User.builder()
                        .username("admin")
                        .password(passwordEncoder.encode("admin"))
                        .roles(roles)
                        .build();
                userRepository.save(user);
                log.warn("Create default admin account with username: admin, password: admin");
            }
        };
    }
}

