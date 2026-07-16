package com.GA.gymApp.booking.dto;

import com.GA.gymApp.booking.enums.BookingStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record EnrollResponseDTO(
        Long sessionId,
        Long userId,
        String name,
        LocalDate date,
        LocalTime time,
        BookingStatus status
) {
}
