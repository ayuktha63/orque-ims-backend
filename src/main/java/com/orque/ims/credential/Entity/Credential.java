package com.orque.ims.credential.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "credentials")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Credential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private Long employeeId;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // PLAIN TEXT

    @Column(nullable = false)
    private String role; // ADMIN, HR, FINANCE, USER, SYSTEM_ADMIN
}