package com.orque.ims.finance.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;

@Entity
@Table(name = "finance_entries")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class FinanceEntry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private String type; // INCOME or EXPENSE

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false, length = 20)
    private String paymentMode; // CASH, UPI, BANK

    private String description;

    private Instant createdAt = Instant.now();
}