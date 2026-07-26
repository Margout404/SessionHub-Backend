package com.GA.gymApp.user.dto;

import com.GA.gymApp.user.Role;

public record UserInfo(
        Long id,
        String firstName,
        String lastName,
        String email,
        Role role
) {

}
