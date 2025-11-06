package dev.dmbiee.securenote.features.auth;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.dmbiee.securenote.core.security.JwtService;
import dev.dmbiee.securenote.features.auth.dtos.AuthRequest;
import dev.dmbiee.securenote.features.auth.dtos.AuthResponse;
import dev.dmbiee.securenote.features.user.UserEntity;
import dev.dmbiee.securenote.features.user.UserService;
import dev.dmbiee.securenote.features.user.dtos.UserRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public AuthResponse login(AuthRequest request) {
        UserEntity user = userService.getByUsername(request.username());

        if (!userService.checkPassword(user, request.password())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);

    }

    public AuthResponse register(UserRequest request) {
        if (userService.existsByUsername(request.username())) {
            throw new IllegalArgumentException("User already exists");
        }

        UserEntity user = new UserEntity();
        user.setUsername(request.username());
        user.setPassword(passwordEncoder.encode(request.password()));
        userService.save(user);

        var token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }
}
