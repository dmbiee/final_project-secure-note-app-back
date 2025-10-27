package dev.dmbiee.securenote.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import dev.dmbiee.securenote.features.user.UserEntity;
import dev.dmbiee.securenote.features.user.UserRepository;

@Configuration
public class AppConfig {

    @Bean
    CommandLineRunner run(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        String encodedpasswort = passwordEncoder.encode("testpassword");
        return args -> {
            if (userRepository.findByUsername("testuser").isEmpty()) {
                UserEntity user = new UserEntity(
                        null,
                        "testuser",
                        encodedpasswort);
                userRepository.save(user);
                System.out.println("✅ Тестовий користувач створений: testuser / testpassword");
            }
        };
    }
}