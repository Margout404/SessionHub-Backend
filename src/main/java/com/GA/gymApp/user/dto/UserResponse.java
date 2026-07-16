package com.GA.gymApp.user.dto;

import com.GA.gymApp.user.Role;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String email,
        Role role,
        LocalDateTime createdAt,
        Boolean active
) {
}
