package com.smartstock.controller;

import com.smartstock.dto.AuthResponseDTO;
import com.smartstock.dto.LoginRequestDTO;
import com.smartstock.dto.RegisterRequestDTO;
import com.smartstock.response.ApiResponse;
import com.smartstock.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ApiResponse<AuthResponseDTO> register(@Valid @RequestBody RegisterRequestDTO dto) {
        AuthResponseDTO response = authService.register(dto);
        return new ApiResponse<>("User registered successfully", response);
    }

    @PostMapping("/login")
    public ApiResponse<AuthResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        AuthResponseDTO response = authService.login(dto);
        return new ApiResponse<>("Login successful", response);
    }
}