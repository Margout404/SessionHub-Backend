package com.GA.gymApp.training_session.service;

import com.GA.gymApp.exceptions.Exceptions;
import com.GA.gymApp.trainer.model.Trainer;
import com.GA.gymApp.trainer.repository.TrainerRepository;
import com.GA.gymApp.training_room.model.TrainingRoom;
import com.GA.gymApp.training_room.repository.TrainingRoomRepository;
import com.GA.gymApp.training_session.TrainingSessionStatus;
import com.GA.gymApp.training_session.dto.*;
import com.GA.gymApp.training_session.model.TrainingSession;
import com.GA.gymApp.training_session.repository.TrainingSessionRepository;
import com.GA.gymApp.training_type.model.TrainingType;
import com.GA.gymApp.training_type.repository.TrainingTypeRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;


@Service
public class TrainingSessionService {

    private final TrainingSessionRepository repository;
    private final TrainingRoomRepository roomRepository;
    private final TrainingTypeRepository typeRepository;
    private final TrainerRepository trainerRepository;


    public TrainingSessionService(TrainingSessionRepository repository, TrainingRoomRepository roomRepository, TrainingTypeRepository typeRepository, TrainerRepository trainerRepository) {
        this.repository = repository;
        this.roomRepository = roomRepository;
        this.typeRepository = typeRepository;
        this.trainerRepository = trainerRepository;
    }

    @Transactional
    public CreateSessionResponseDTO createSession(CreateSessionDTO dto) {

        checkAvailability(dto);

        TrainingSession trainingSession = new TrainingSession();
        trainingSession.setTrainingRoom(roomRepository.findById(
                dto.roomId()).orElseThrow(
                        () -> new Exceptions.ResourceNotFoundException("No such Training Room")));

        trainingSession.setTrainer(trainerRepository.findById(
                dto.trainerId()).orElseThrow(
                        () -> new Exceptions.ResourceNotFoundException("No such Trainer")));

        trainingSession.setTrainingType(typeRepository.findById(
                dto.trainingTypeId()).orElseThrow(
                        () -> new Exceptions.ResourceNotFoundException("No such Training Type")));

        trainingSession.setDate(dto.date());
        trainingSession.setStartTime(dto.startTime());
        trainingSession.setEndTime(dto.endTime());
        trainingSession.setMaxParticipants(dto.maxParticipants());
        trainingSession.setStatus(dto.status());

        repository.save(trainingSession);

        return new CreateSessionResponseDTO(
                trainingSession.getTrainer().getId(),
                trainingSession.getTrainingRoom().getId(),
                trainingSession.getTrainingType().getId(),
                trainingSession.getDate(),
                trainingSession.getStartTime(),
                trainingSession.getEndTime(),
                trainingSession.getMaxParticipants(),
                trainingSession.getStatus(),
                trainingSession.getId(),
                "Successfully generated Training Session");

    }

    public String updateStatus(Long sessionId, TrainingSessionStatus status) {

        TrainingSession trainingSession = repository.findById(sessionId).orElseThrow(
                () -> new Exceptions.ResourceNotFoundException("no such Session Found"));

        trainingSession.setStatus(status);
        LocalDate today = LocalDate.now();
        LocalTime now = LocalTime.now();

        if (trainingSession.getDate().isAfter(today)) {
            trainingSession.setStatus(TrainingSessionStatus.SCHEDULED);

        } else if (trainingSession.getDate().isBefore(today)) {
            trainingSession.setStatus(TrainingSessionStatus.DONE);

        } else if (now.isBefore(trainingSession.getStartTime())) {
            trainingSession.setStatus(TrainingSessionStatus.SCHEDULED);

        } else if (now.isBefore(trainingSession.getEndTime())) {
            trainingSession.setStatus(TrainingSessionStatus.LIVE);

        } else {
            trainingSession.setStatus(TrainingSessionStatus.DONE);
        }
        repository.save(trainingSession);

        return "Training Status updated to :" + trainingSession.getStatus();
    }

    public WeeklySessionsResponseDTO generateWeeklySessions(GenerateWeeklySessionsRequestDTO request) {

        List<TrainingSession> sessions = new ArrayList<>();
        TrainingRoom room = null;
        TrainingType trainingType = null;
        Trainer trainer = null;

        if (request.defaultRoomId() != null) {
            room = roomRepository.findById(request.defaultRoomId()).orElseThrow(
                    () -> new Exceptions.ResourceNotFoundException("No such Training Room"));
        }

        if (request.defaultTrainingTypeId() != null) {
            trainingType = typeRepository.findById(request.defaultTrainingTypeId()).orElseThrow(
                    () -> new Exceptions.ResourceNotFoundException("No such Training Type"));
        }
        if (request.defaultTrainerId() != null) {
            trainer = trainerRepository.findById(request.defaultTrainerId()).orElseThrow(
                    () -> new Exceptions.ResourceNotFoundException("No such Trainer"));
        }

        for (int i = 0; i < 7; i++) {
            TrainingSession session = new TrainingSession();

            session.setDate(request.weekStartDate().plusDays(i));
            session.setStartTime(request.defaultStartTime());
            session.setEndTime(request.defaultEndTime());
            session.setMaxParticipants(request.defaultMaxParticipants());
            session.setTrainingRoom(room);
            session.setTrainingType(trainingType);
            session.setTrainer(trainer);
            session.setStatus(TrainingSessionStatus.DRAFT);

            sessions.add(session);
        }

        List<TrainingSession> savedSessions = repository.saveAll(sessions);

        List<Long> sessionIds = savedSessions.stream().map(TrainingSession::getId).toList();

        return new WeeklySessionsResponseDTO(
                request.weekStartDate(),
                request.weekStartDate().plusDays(6),
                sessions.size(),
                sessionIds,
                "Weekly schedule generated successfully.");
    }

    public CreateSessionResponseDTO updateSession(Long sessionId, UpdateSessionDTO dto) {

        System.out.println("Session id = " + sessionId);
        TrainingSession session = repository.findById(sessionId)
                .orElseThrow(() -> new Exceptions.ResourceNotFoundException("No such Session"));

        TrainingRoom room = roomRepository.findById(dto.roomId())
                .orElseThrow(() -> new Exceptions.ResourceNotFoundException("No such Training Room"));

        if (dto.trainerId() != null) {
            session.setTrainer(
                    trainerRepository.findById(dto.trainerId())
                            .orElseThrow(() -> new Exceptions.ResourceNotFoundException("No such Trainer"))
            );
        }

        session.setTrainingRoom(
                roomRepository.findById(dto.roomId())
                        .orElseThrow(() -> new Exceptions.ResourceNotFoundException("No such Training Room"))
        );

        if (dto.trainingTypeId() != null) {
            session.setTrainingType(
                    typeRepository.findById(dto.trainingTypeId())
                            .orElseThrow(() -> new Exceptions.ResourceNotFoundException("No such Training Type"))
            );
        }

        if (dto.maxParticipants() > room.getCapacity()) {
            throw new Exceptions.ConflictException(
                    "Max participants cannot exceed room capacity"
            );
        }

        if (dto.date() != null) {
            session.setDate(dto.date());
        }

        if (dto.startTime() != null) {
            session.setStartTime(dto.startTime());
        }

        if (dto.endTime() != null) {
            session.setEndTime(dto.endTime());
        }

        if (dto.maxParticipants() != null) {
            session.setMaxParticipants(dto.maxParticipants());
        }

        if (dto.status() != null) {
            session.setStatus(dto.status());
        }

        TrainingSession saved = repository.save(session);

        return new CreateSessionResponseDTO(
                saved.getTrainer() != null ? saved.getTrainer().getId() : null,
                saved.getTrainingRoom() != null ? saved.getTrainingRoom().getId() : null,
                saved.getTrainingType() != null ? saved.getTrainingType().getId() : null,
                saved.getDate(),
                saved.getStartTime(),
                saved.getEndTime(),
                saved.getMaxParticipants(),
                saved.getStatus(),
                saved.getId(),
                "Training Session updated successfully"
        );
    }


    public PublishSessionsResponseDTO publishSessions(List<Long> sessionIds){

        HashSet<Long> ids = new HashSet<>(sessionIds);

        List<TrainingSession> sessions=repository.findAllById(ids);

        for (TrainingSession session : sessions){

            if(session.getTrainingRoom()== null){
                throw new Exceptions.BadRequestException(
                        "Session " + session.getId() + "has no Training Room"
                );
            }
            if(session.getTrainingType()== null){
                throw new Exceptions.BadRequestException(
                        "Session " + session.getId() + "has no Training Type"
                );
            }
            if(session.getTrainer()== null){
                throw new Exceptions.BadRequestException(
                        "Session " + session.getId() + "has no Trainer"
                );
            }

            session.setStatus(TrainingSessionStatus.SCHEDULED);

        }
        repository.saveAll(sessions);
        return new PublishSessionsResponseDTO(
                sessions.size(),
                ids,
                "Sessions published successfully"
        );




    }


    public String deleteSession(Long sessionId) {

        if (repository.existsById(sessionId)) {
            repository.deleteById(sessionId);
            return "Session " + sessionId + " deleted";
        } else {
            throw new Exceptions.ResourceNotFoundException("No session with this id");
        }

    }

    private void checkAvailability(CreateSessionDTO dto) {


        if (!dto.startTime().isBefore(dto.endTime())) {
            throw new Exceptions.ConflictException("Start time must be before end time");
        }


        List<TrainingSession> overlappingSessions =
                repository.findByDateAndStartTimeLessThanAndEndTimeGreaterThan(
                        dto.date(),
                        dto.endTime(),
                        dto.startTime()
                );

        boolean roomOccupied = overlappingSessions.stream()
                .anyMatch(session ->
                        session.getTrainingRoom() != null &&
                                Objects.equals(
                                        session.getTrainingRoom().getId(),
                                        dto.roomId()
                                )
                );
        if (roomOccupied) {
            throw new Exceptions.ConflictException("Training room is occupied at this time");
        }

        boolean trainerOccupied = overlappingSessions.stream()
                .anyMatch(trainingSession ->
                        trainingSession.getTrainer() != null &&
                                Objects.equals(dto.trainerId(),
                                        trainingSession.getTrainer().getId())
                );

        if (trainerOccupied) {
            throw new Exceptions.ConflictException("Trainer is occupied at this time");
        }

    }
}
