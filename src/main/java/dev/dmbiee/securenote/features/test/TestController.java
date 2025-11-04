package dev.dmbiee.securenote.features.test;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String hello() {
        return "Hello World! Тільки для авторизованих користувачів.";
    }
}
