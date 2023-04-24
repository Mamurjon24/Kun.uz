package com.example.controller;

import com.example.dto.auth.AuthDTO;
import com.example.dto.auth.AuthResponseDTO;
import com.example.dto.register.RegistrationDTO;
import com.example.dto.register.RegistrationResponseDTO;
import com.example.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthDTO dto) {
        return ResponseEntity.ok(authService.login(dto));
    }
   /*
   @PostMapping("register")
    public ResponseEntity<ProfileDTO> register(@RequestBody ProfileDTO dto) {
        return ResponseEntity.ok(authService.register(dto));
    }
    */
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseDTO> registration(@RequestBody RegistrationDTO dto) {
        return ResponseEntity.ok(authService.registration(dto));
    }
    @PostMapping("/verification")
    public ResponseEntity<RegistrationResponseDTO> verification(@PathVariable("email") String text) {
        return ResponseEntity.ok(authService.emailVerification(text));
    }

}
