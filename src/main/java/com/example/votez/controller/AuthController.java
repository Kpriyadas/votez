package com.example.votez.controller;

import com.example.votez.dto.LoginRequestDto;
import com.example.votez.dto.LoginResponseDto;
import com.example.votez.entity.Voter;
import com.example.votez.repository.VoterRepository;
import com.example.votez.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private VoterRepository voterRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto request) {
        System.out.println("Login endpoint called with email: " + request.getEmail());

        // Find voter by email and secretCode
        Voter voter = voterRepository.findByEmailAndSecretCode(
                request.getEmail(), request.getSecretCode()
        ).orElseThrow(() -> new RuntimeException("Invalid email or secret code"));

        // Generate JWT using email and role
        String token = jwtUtil.generateToken(
                voter.getEmail().toLowerCase(),
                voter.getRole().toLowerCase()  // Use role, not secret code
        );

        return new LoginResponseDto(token, voter.getId(), voter.getName());
    }
}
