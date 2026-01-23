package com.orque.ims.payroll.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record PayrollResponse(
        Long id,
        String payrollCode,
        String month,
        Long employeeId,
        String employeeCode,
        String employeeName,
        BigDecimal basic,
        BigDecimal allowances,
        BigDecimal deductions,
        BigDecimal netPay,
        String notes,
        Instant createdAt
) {}