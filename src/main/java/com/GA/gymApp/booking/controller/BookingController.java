package com.GA.gymApp.booking.controller;

import com.GA.gymApp.booking.dto.AvailableSessionsDTO;
import com.GA.gymApp.booking.dto.EnrollResponseDTO;
import com.GA.gymApp.booking.service.BookingService;
import com.GA.gymApp.security.generic.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService service;

    public BookingController(BookingService service) {
        this.service = service;
    }

    @GetMapping("/available-sessions")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<List<AvailableSessionsDTO>> seeAvailableSessions(@RequestParam LocalDate from,
                                                                           @RequestParam LocalDate until){
        List<AvailableSessionsDTO> dto= service.seeAvailableSessions(from,until);

        return ResponseEntity.ok(dto);
    }

    @PostMapping("/enroll/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','USER')")
    public ResponseEntity<EnrollResponseDTO> enroll(@PathVariable Long id,
                                                    @AuthenticationPrincipal CustomUserDetails userDetails){

        EnrollResponseDTO dto= service.enroll(id,userDetails.getUser());

        return ResponseEntity.ok(dto);
    }
}
