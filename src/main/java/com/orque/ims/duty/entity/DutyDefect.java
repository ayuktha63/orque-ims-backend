package com.orque.ims.duty.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name="duty_defects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DutyDefect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobId;

    @Column(length=2000)
    private String issue;

    private String status; // OPEN / CLOSED

    // ⭐ USER WHO CREATED DEFECT (from JWT)
    @Column(nullable = false)
    private String createdBy;

    private Instant createdAt = Instant.now();
}
