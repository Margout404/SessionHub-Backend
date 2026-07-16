package com.GA.gymApp.training_session.dto;

import java.util.Set;

public record PublishSessionsResponseDTO(
        Integer publishedSessions,
        Set<Long> ids,
        String message

) {
}
