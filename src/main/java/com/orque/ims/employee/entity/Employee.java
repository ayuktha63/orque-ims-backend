package com.orque.ims.employee.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(name = "employees")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String employeeCode;

    @Column(nullable = false)
    private String name;

    // ✅ NEW COLUMN
    @Column(unique = true, nullable = false)
    private String email;

    private String department;
    private String role;

    @Column(nullable = false)
    private LocalDate joinDate;

    @Column(nullable = false, length = 20)
    private String status;

    private Instant createdAt = Instant.now();
}