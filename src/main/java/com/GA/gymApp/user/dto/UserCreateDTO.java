package com.GA.gymApp.user.dto;

import com.GA.gymApp.user.Role;

public record UserCreateDTO (
        String firstName,
        String lastName,
        String email,
        Role role,
        String password){
}
