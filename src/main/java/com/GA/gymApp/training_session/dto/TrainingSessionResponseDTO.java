package com.GA.gymApp.training_session.dto;

import com.GA.gymApp.training_session.TrainingSessionStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record TrainingSessionResponseDTO(
        Long sessionId,

        Long trainerId,
        String trainerName,

        Long roomId,
        String roomName,

        Long trainingTypeId,
        String trainingTypeName,

        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,

        Integer maxParticipants,
        TrainingSessionStatus status,
        Integer currentEnrollments
) {
}
