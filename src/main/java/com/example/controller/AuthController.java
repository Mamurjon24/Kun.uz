package com.example.controller;

import com.example.dto.AuthDTO;
import com.example.dto.AuthResponseDTO;
import com.example.dto.ProfileDTO;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
    @PostMapping("register")
    public ResponseEntity<ProfileDTO> register(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }

}
