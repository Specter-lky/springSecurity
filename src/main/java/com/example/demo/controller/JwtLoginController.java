package com.example.demo.controller;

import com.example.demo.Service.JwtAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtLoginController {
    @Autowired
    JwtAuthService jwtAuthService;
    @PostMapping({"/login", "/"})
    public String login(String username, String password) {
        String token = jwtAuthService.login(username, password);
        return token;
    }
}
