package com.GA.gymApp.user.dto;

import com.GA.gymApp.booking.enums.BookingStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingResponseDto(
        BookingStatus status,
        LocalDate date,
        LocalTime time,
        String firstName,
        String lastName,
        String trainerFirstName,
        String trainerLastName
) {
}
