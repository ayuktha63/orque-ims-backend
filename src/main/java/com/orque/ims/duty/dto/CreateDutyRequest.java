package com.orque.ims.duty.dto;

import java.time.LocalDate;

public record CreateDutyRequest(
        String title,
        String description,
        LocalDate deadline,
        Long employeeId
) {}
