package com.example.controller;

import com.example.dto.LoginRequestDto;
import com.example.dto.LoginResponseDto;
import com.example.dto.RegisterRequestDto;
import com.example.service.AuthClientService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@AllArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    private final AuthClientService authClientService;

    @PostMapping("/register")
    public String registerUser(
        @RequestBody RegisterRequestDto request) {
        return authClientService.registerUser(request.getUsername(), request.getPassword());
    }

    @PostMapping("/login")
    public LoginResponseDto loginUser(
        @RequestBody LoginRequestDto request) {
        return authClientService.loginUser(request.getUsername(), request.getPassword());
    }
}

