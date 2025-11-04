package dev.dmbiee.securenote.features.auth;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;

import dev.dmbiee.securenote.features.auth.dtos.AuthRequest;
import dev.dmbiee.securenote.features.auth.dtos.AuthResponse;
import dev.dmbiee.securenote.features.user.dtos.UserRequest;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    // @PostMapping("/login")
    // public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
    // AuthResponse token = authService.login(request);
    // return ResponseEntity.ok(token);
    // }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest request, HttpServletResponse response) {

        String token = authService.login(request).token();

        Cookie cookie = new Cookie("jwt", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(true); // true для HTTPS
        cookie.setPath("/");
        cookie.setMaxAge(24 * 60 * 60); // 1 день

        response.addCookie(cookie);

        return ResponseEntity.ok(Map.of("message", "Login success"));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody UserRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("/check")
    public ResponseEntity<String> checkAuth(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            return ResponseEntity.ok("User is authenticated");
        } else {
            return ResponseEntity.status(403).body("User is not authenticated");
        }
    }
}
