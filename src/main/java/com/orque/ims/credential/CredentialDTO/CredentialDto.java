package com.orque.ims.credential.CredentialDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CredentialDto {
    private Long employeeId;
    private String name;        // Matches Employee.name
    private String department;
    private String password;
    private String username;    // Null if no login exists
    private String role;        // Null if no login exists
    private boolean hasAccess;  // True if username exists
}