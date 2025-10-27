package dev.dmbiee.securenote.features.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Знайти користувача за іменем.
     */
    public UserEntity getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    /**
     * Перевірити пароль користувача.
     */
    public boolean checkPassword(UserEntity user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }
}