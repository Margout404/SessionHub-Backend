package com.GA.gymApp.booking.dto;

import com.GA.gymApp.training_session.TrainingSessionStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AvailableSessionsDTO(
        Long sessionId,
        String trainerFirstName,
        String trainerLastName,
        Long roomId,
        Long trainingType,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        Integer maxParticipants,
        Integer currentParticipants,
        TrainingSessionStatus status
) {
}
