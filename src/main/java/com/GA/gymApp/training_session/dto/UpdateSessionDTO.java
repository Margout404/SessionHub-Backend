package com.GA.gymApp.training_session.dto;

import com.GA.gymApp.training_session.TrainingSessionStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record UpdateSessionDTO(Long trainerId,
                               Long roomId,
                               Long trainingTypeId,
                               LocalDate date,
                               LocalTime startTime,
                               LocalTime endTime,
                               Integer maxParticipants,
                               TrainingSessionStatus status) {
}
