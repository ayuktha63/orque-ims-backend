package com.orque.ims.finance.dto;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;

public record FinanceResponse(
        Long id,
        LocalDate date,
        String type,
        String category,
        BigDecimal amount,
        String paymentMode,
        String description,
        Instant createdAt
) {}
