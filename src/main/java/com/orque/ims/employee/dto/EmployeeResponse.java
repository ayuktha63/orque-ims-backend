package com.orque.ims.employee.dto;

import java.time.Instant;
import java.time.LocalDate;

public record EmployeeResponse(
        Long id,
        String employeeCode,
        String name,
        String email,     // ✅ NEW FIELD
        String department,
        String role,
        LocalDate joinDate,
        String status,
        Instant createdAt
) {}