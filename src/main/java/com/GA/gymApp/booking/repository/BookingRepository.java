package com.GA.gymApp.booking.repository;

import com.GA.gymApp.booking.enums.BookingStatus;
import com.GA.gymApp.booking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {


    Integer countByTrainingSession_IdAndStatus(Long trainingSessionId, BookingStatus status);

    boolean existsByUser_IdAndTrainingSession_IdAndStatusNot(Long userId,Long sessionId,BookingStatus status);

    List<Booking> findAllByUser_IdOrderByTrainingSession_DateAscTrainingSession_StartTimeAsc(
            Long userId
    );



}
