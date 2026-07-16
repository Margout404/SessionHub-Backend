package com.GA.gymApp.user.dto;

public record LoginRequest(
        String email,
        String password
) {}
