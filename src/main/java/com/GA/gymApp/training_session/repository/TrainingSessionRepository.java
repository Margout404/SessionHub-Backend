package com.GA.gymApp.training_session.repository;

import com.GA.gymApp.training_session.TrainingSessionStatus;
import com.GA.gymApp.training_session.model.TrainingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface TrainingSessionRepository extends JpaRepository<TrainingSession,Long> {
    boolean findByDate(LocalDate date);

    Optional<TrainingSession> findByStartTimeAndDate(LocalTime startTime, LocalDate date);

    List<TrainingSession> findByDateAndStartTimeLessThanAndEndTimeGreaterThan(
            LocalDate date,
            LocalTime newEndTime,
            LocalTime newStartTime
    );

//    This method searches dates without the params
    List<TrainingSession> findByDateAfterAndDateBeforeAndStatusEquals(LocalDate dateAfter, LocalDate dateBefore, TrainingSessionStatus status);


    List<TrainingSession> findByDateBetweenAndStatusOrderByDateAscStartTimeAsc(LocalDate dateAfter, LocalDate dateBefore, TrainingSessionStatus status);



}
