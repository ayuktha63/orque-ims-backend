package com.orque.ims.payroll.entity;

import com.orque.ims.employee.entity.Employee;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "payroll")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String payrollCode;

    @Column(nullable = false)
    private String month; // Format: YYYY-MM

    @ManyToOne(fetch = FetchType.EAGER) // Fixed: Eager prevents the LazyInitializationException
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Column(nullable = false)
    private BigDecimal basic;

    @Column(nullable = false)
    private BigDecimal allowances;

    @Column(nullable = false)
    private BigDecimal deductions;

    @Column(nullable = false)
    private BigDecimal netPay;

    private String notes;

    private Instant createdAt = Instant.now();
}