package com.GA.gymApp.training_session.model;

import com.GA.gymApp.trainer.model.Trainer;
import com.GA.gymApp.training_room.model.TrainingRoom;
import com.GA.gymApp.training_session.TrainingSessionStatus;
import com.GA.gymApp.training_type.model.TrainingType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name= "training_session")
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;

    private LocalTime startTime;

    private LocalTime endTime;

    private Integer maxParticipants;

    @Enumerated(EnumType.STRING)
    private TrainingSessionStatus status;

    @ManyToOne(optional= true)
    @JoinColumn(name = "trainer_id",nullable = true)
    private Trainer trainer;

    @ManyToOne(optional= true)
    @JoinColumn(name = "training_room_id")
    private TrainingRoom trainingRoom;

    @ManyToOne(optional= true)
    @JoinColumn(name = "training_type_id",nullable = true)
    private TrainingType trainingType;
}
