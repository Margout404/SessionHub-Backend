package com.GA.gymApp.training_session.controller;

import com.GA.gymApp.training_session.dto.*;
import com.GA.gymApp.training_session.service.TrainingSessionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/sessions")
public class TrainingSessionController {

    private final TrainingSessionService service;

    public TrainingSessionController(TrainingSessionService service) {
        this.service = service;
    }

    @PostMapping("/create-session")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreateSessionResponseDTO> createSession(@RequestBody CreateSessionDTO dto){
        CreateSessionResponseDTO response= service.createSession(dto);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/generate-weekly-sessions")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<WeeklySessionsResponseDTO> generateWeeklySessions(@RequestBody GenerateWeeklySessionsRequestDTO dto){
        WeeklySessionsResponseDTO response= service.generateWeeklySessions(dto);

        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PatchMapping("/update-session/{sessionId}")
    public ResponseEntity<CreateSessionResponseDTO> updateSession(
            @PathVariable Long sessionId,
            @RequestBody UpdateSessionDTO dto
    ) {
        CreateSessionResponseDTO response = service.updateSession(sessionId, dto);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete-session/{sessionId}")
    public ResponseEntity<String> deleteSession(@PathVariable Long sessionId){
        String response = service.deleteSession(sessionId);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/publish-sessions")
    public ResponseEntity<PublishSessionsResponseDTO> publishSessions(@RequestBody List<Long> ids){
        PublishSessionsResponseDTO responseDTO= service.publishSessions(ids);
        return ResponseEntity.ok(responseDTO);
    }
}
