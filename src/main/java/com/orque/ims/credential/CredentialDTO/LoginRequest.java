package com.orque.ims.credential.CredentialDTO;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
    private String userType; // "ORQUE" or "BUSINESS"
}