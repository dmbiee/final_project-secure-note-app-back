package dev.dmbiee.securenote.features.auth;

import org.springframework.stereotype.Service;

import dev.dmbiee.securenote.core.security.JwtService;
import dev.dmbiee.securenote.features.auth.dtos.AuthRequest;
import dev.dmbiee.securenote.features.auth.dtos.AuthResponse;
import dev.dmbiee.securenote.features.user.UserEntity;
import dev.dmbiee.securenote.features.user.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;

    public AuthResponse login(AuthRequest request) {
        UserEntity user = userService.getByUsername(request.username());

        if (!userService.checkPassword(user, request.password())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtService.generateToken(user.getUsername());
        return new AuthResponse(token);
    }
}
