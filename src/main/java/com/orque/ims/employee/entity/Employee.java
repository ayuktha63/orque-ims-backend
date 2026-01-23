package com.orque.ims.employee.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(name = "employees")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String employeeCode; // This is the field used by Payroll

    @Column(nullable = false)
    private String name;

    private String department;
    private String role;

    @Column(nullable = false)
    private LocalDate joinDate;

    @Column(nullable = false, length = 20)
    private String status; // ACTIVE or INACTIVE

    private Instant createdAt = Instant.now();
}