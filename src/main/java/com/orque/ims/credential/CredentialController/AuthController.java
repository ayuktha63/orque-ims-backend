package com.orque.ims.credential.CredentialController;

import com.orque.ims.config.JwtUtil;
import com.orque.ims.credential.CredentialDTO.LoginRequest;
import com.orque.ims.credential.CredentialDTO.LoginResponse;
import com.orque.ims.credential.CredentialRepository.CredentialRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final CredentialRepository repository;
    private final JwtUtil jwtUtil;

    public AuthController(CredentialRepository repository, JwtUtil jwtUtil) {
        this.repository = repository;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

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