package com.orque.ims.credential.CredentialController;

import com.orque.ims.config.JwtUtil;
import com.orque.ims.credential.CredentialDTO.LoginRequest;
import com.orque.ims.credential.CredentialDTO.LoginResponse;
import com.orque.ims.credential.CredentialRepository.CredentialRepository;
import com.orque.ims.client.repository.ClientRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CredentialRepository repository;
    private final ClientRepository clientRepository;
    private final JwtUtil jwtUtil;

    public AuthController(CredentialRepository repository, ClientRepository clientRepository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.clientRepository = clientRepository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        String userType = request.getUserType() != null ? request.getUserType() : "ORQUE";

        if ("BUSINESS".equalsIgnoreCase(userType)) {
            // Client Portal Login
            return clientRepository.findByUsername(request.getUsername())
                    .map(client -> {
                        if (client.getPassword() != null && client.getPassword().equals(request.getPassword())) {
                            
                            // Check if account is active
                            if ("INACTIVE".equalsIgnoreCase(client.getStatus().name())) {
                                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Client account is inactive");
                            }

                            String token = jwtUtil.generateToken(client.getUsername(), "CLIENT");
                            return ResponseEntity.ok(
                                    new LoginResponse(token, "CLIENT", client.getId())
                            );
                        }
                        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
                    })
                    .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Client not found"));
        }

        // Employee Login (Default)
        return repository.findByUsername(request.getUsername())
                .map(user -> {

                    // ✅ Plain text comparison
                    if (request.getPassword().equals(user.getPassword())) {

                        String token = jwtUtil.generateToken(
                                user.getUsername(),
                                user.getRole()
                        );

                        return ResponseEntity.ok(
                                new LoginResponse(
                                        token,
                                        user.getRole(),
                                        user.getEmployeeId()
                                )
                        );
                    }

                    return ResponseEntity
                            .status(HttpStatus.UNAUTHORIZED)
                            .body("Invalid credentials");

                })
                .orElse(ResponseEntity
                        .status(HttpStatus.UNAUTHORIZED)
                        .body("User not found"));
    }
}