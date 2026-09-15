package com.GA.gymApp.booking.dto;

import com.GA.gymApp.booking.enums.BookingStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record MyBookingResponseDTO(
        Long bookingId,
        Long sessionId,
        String trainingTypeName,
        String trainerName,
        String roomName,
        LocalDate date,
        LocalTime startTime,
        LocalTime endTime,
        BookingStatus bookingStatus
) {
}
