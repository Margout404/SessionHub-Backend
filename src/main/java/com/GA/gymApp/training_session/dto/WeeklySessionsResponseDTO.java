package com.GA.gymApp.training_session.dto;

import java.time.LocalDate;
import java.util.List;

public record WeeklySessionsResponseDTO(
        LocalDate fromDate,
        LocalDate toDate,
        Integer generatedSessions,
        List<Long> sessionIds,
        String message
) {
}
