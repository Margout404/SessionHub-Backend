package com.GA.gymApp.booking.model;

import com.GA.gymApp.booking.enums.BookingStatus;
import com.GA.gymApp.training_session.model.TrainingSession;
import com.GA.gymApp.user.model.User;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
@Table(
        name = "booking",
        uniqueConstraints = {
        @UniqueConstraint(
                columnNames = {"user_id", "training_session_id"}
        )
})
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime bookedAt;

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "user_id",nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "training_session_id",nullable = false)
    private TrainingSession trainingSession;

}
