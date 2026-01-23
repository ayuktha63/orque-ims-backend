package com.orque.ims.finance.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;

public record CreateFinanceRequest(
        @NotNull(message = "Date is required") LocalDate date,
        @NotBlank(message = "Type (INCOME/EXPENSE) is required") String type,
        @NotBlank(message = "Category is required") String category,
        @NotNull @Positive(message = "Amount must be greater than zero") BigDecimal amount,
        @NotBlank(message = "Payment mode is required") String paymentMode,
        String description
) {}

