package com.GA.gymApp.training_session.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public record GenerateWeeklySessionsRequestDTO(
        LocalDate weekStartDate,
        LocalTime defaultStartTime,
        LocalTime defaultEndTime,
        Integer defaultMaxParticipants,
        Long defaultRoomId,
        Long defaultTrainingTypeId,
        Long defaultTrainerId
) {
}
