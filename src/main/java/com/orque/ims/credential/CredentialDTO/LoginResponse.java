package com.orque.ims.credential.CredentialDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
    private String token;
    private String role;
    private Long userId;
}