package com.orque.ims.employee.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;
import java.time.Instant;

public record CreateEmployeeRequest(
        @NotBlank(message = "Name is required") String name,
        String department,
        String role,
        @NotNull(message = "Join date is required") LocalDate joinDate,
        @NotBlank @Pattern(regexp = "ACTIVE|INACTIVE") String status
) {}

