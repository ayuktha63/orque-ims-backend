package com.orque.ims.duty.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.orque.ims.employee.entity.Employee;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "duties")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Duty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobId;

    private String title;

    @Column(length = 1000)
    private String description;

    // 🔥 THIS FIELD WAS MISSING
    private LocalDate deadline;

    private String status;

    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
    private Employee assignedTo;

    // =========================
    // GETTERS & SETTERS
    // =========================

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getJobId() { return jobId; }

    public void setJobId(String jobId) { this.jobId = jobId; }

    public String getTitle() { return title; }

    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }

    public void setDescription(String description) { this.description = description; }

    public LocalDate getDeadline() { return deadline; }

    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Employee getAssignedTo() { return assignedTo; }

    public void setAssignedTo(Employee assignedTo) { this.assignedTo = assignedTo; }
}
