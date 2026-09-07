package com.GA.gymApp.training_session.mappers;

import com.GA.gymApp.training_session.dto.TrainingSessionResponseDTO;
import com.GA.gymApp.training_session.model.TrainingSession;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TrainingSessionMapper {

    public TrainingSessionResponseDTO toResponseDTO(
            TrainingSession session
    ) {
        return new TrainingSessionResponseDTO(
                session.getId(),

                session.getTrainer().getId(),
                session.getTrainer().getFirstName()
                        + " "
                        + session.getTrainer().getLastName(),

                session.getTrainingRoom().getId(),
                session.getTrainingRoom().getName(),

                session.getTrainingType().getId(),
                session.getTrainingType().getName(),

                session.getDate(),
                session.getStartTime(),
                session.getEndTime(),

                session.getMaxParticipants(),
                session.getStatus()
        );
    }
}
