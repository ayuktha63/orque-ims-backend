package com.orque.ims.employee.dto;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record CreateEmployeeRequest(

        @NotBlank(message = "Name is required")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,   // ✅ NEW FIELD

        String department,
        String role,

        @NotNull(message = "Join date is required")
        LocalDate joinDate,

        @NotBlank
        @Pattern(regexp = "ACTIVE|INACTIVE")
        String status

) {}