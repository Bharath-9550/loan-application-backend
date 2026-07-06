package com.bharath.loanapplication.controller;

import com.bharath.loanapplication.model.AuthResponse;
import com.bharath.loanapplication.model.LoginRequest;
import com.bharath.loanapplication.model.RegisterRequest;
import com.bharath.loanapplication.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public AuthResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }
}