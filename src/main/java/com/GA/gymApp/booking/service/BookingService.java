package com.GA.gymApp.booking.service;

import com.GA.gymApp.booking.dto.AvailableSessionsDTO;
import com.GA.gymApp.booking.dto.EnrollResponseDTO;
import com.GA.gymApp.booking.enums.BookingStatus;
import com.GA.gymApp.booking.model.Booking;
import com.GA.gymApp.booking.repository.BookingRepository;
import com.GA.gymApp.exceptions.Exceptions;
import com.GA.gymApp.training_session.TrainingSessionStatus;
import com.GA.gymApp.training_session.model.TrainingSession;
import com.GA.gymApp.training_session.repository.TrainingSessionRepository;
import com.GA.gymApp.user.model.User;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookingService {

    private final BookingRepository repository;
    private final TrainingSessionRepository sessionRepository;

    public BookingService(BookingRepository repository, TrainingSessionRepository sessionRepository) {
        this.repository = repository;
        this.sessionRepository = sessionRepository;
    }

    public List<AvailableSessionsDTO> seeAvailableSessions(LocalDate from, LocalDate until) {

        if (from.isAfter(until)) {
            throw new Exceptions.BadRequestException(
                    "'from' date cannot be after 'until' date"
            );
        }

        List<TrainingSession> sessions = sessionRepository.findByDateBetweenAndStatusOrderByDateAscStartTimeAsc(from, until, TrainingSessionStatus.SCHEDULED);

        List<AvailableSessionsDTO> availableSessionsDTOS = new ArrayList<>();

        for (TrainingSession session : sessions) {
            availableSessionsDTOS.add(new AvailableSessionsDTO(
                    session.getId(),
                    session.getTrainer().getFirstName(),
                    session.getTrainer().getLastName(),
                    session.getTrainingRoom().getId(),
                    session.getTrainingType().getId(),
                    session.getDate(),
                    session.getStartTime(),
                    session.getEndTime(),
                    session.getMaxParticipants(),
                    repository.countByTrainingSession_IdAndStatus(session.getId(), BookingStatus.CONFIRMED),
                    session.getStatus()
            ));
        }
        return availableSessionsDTOS;

    }

    @Transactional
    public EnrollResponseDTO enroll(Long sessionId, User user) {

        TrainingSession session = sessionRepository.findById(sessionId).orElseThrow(
                () -> new Exceptions.ResourceNotFoundException("Session not found"));

        validateEnrollment(session,user);


        BookingStatus status =
                hasAvailableSlot(session)
                        ? BookingStatus.CONFIRMED
                        : BookingStatus.WAITING_LIST;

        Booking booking = createBooking(user, session, status);


        repository.save(booking);

        return new EnrollResponseDTO(
                sessionId,
                user.getId(),
                user.getFirstName() + " " + user.getLastName(),
                session.getDate(),
                session.getStartTime(),
                status
        );


    }

    private void validateEnrollment(TrainingSession session,User user){
        if (session.getStatus() != TrainingSessionStatus.SCHEDULED) {
            throw new Exceptions.BadRequestException(
                    "Enrollment is allowed only for scheduled sessions"
            );
        }

        LocalDateTime sessionStart =
                LocalDateTime.of(
                        session.getDate(),
                        session.getStartTime()
                );

        if (!sessionStart.isAfter(LocalDateTime.now())) {
            throw new Exceptions.BadRequestException(
                    "Cannot enroll in a session that has already started"
            );
        }

        boolean alreadyEnrolled =
                repository.existsByUser_IdAndTrainingSession_IdAndStatusNot(
                        user.getId(),
                        session.getId(),
                        BookingStatus.CANCELLED
                );

        if (alreadyEnrolled) {
            throw new Exceptions.ConflictException(
                    "User is already enrolled in this session"
            );
        }
    }

    private Booking createBooking(User user, TrainingSession session,BookingStatus status) {
        Booking booking = new Booking();
        booking.setUser(user);
        booking.setStatus(status);
        booking.setTrainingSession(session);
        booking.setBookedAt(LocalDateTime.now());

        return booking;
    }

    private boolean hasAvailableSlot(TrainingSession session) {

        Integer confirmedBookings =
                repository.countByTrainingSession_IdAndStatus(
                        session.getId(),
                        BookingStatus.CONFIRMED
                );

        return confirmedBookings < session.getMaxParticipants();
    }
}
