package com.orque.ims.payroll.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreatePayrollRequest(
        @NotBlank(message = "Month is required")
        @Pattern(regexp = "^\\d{4}-\\d{2}$", message = "Format must be YYYY-MM")
        String month,

        @NotNull(message = "Employee ID is required")
        Long employeeId,

        @NotNull @PositiveOrZero BigDecimal basic,
        @NotNull @PositiveOrZero BigDecimal allowances,
        @NotNull @PositiveOrZero BigDecimal deductions,
        String notes
) {}