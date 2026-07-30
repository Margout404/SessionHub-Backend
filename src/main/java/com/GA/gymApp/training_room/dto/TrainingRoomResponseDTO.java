package com.GA.gymApp.training_room.dto;

public record TrainingRoomResponseDTO(
        Long id,
        String name,
        String details,
        Integer capacity
) {
}
