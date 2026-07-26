package com.GA.gymApp.user.mapper;


import com.GA.gymApp.user.Role;
import com.GA.gymApp.user.dto.UserCreateDTO;
import com.GA.gymApp.user.dto.UserResponse;
import com.GA.gymApp.user.model.User;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;


    public User toEntity(UserCreateDTO dto) {
        if ( dto == null ) {
            return null;
        }

        User user = new User();

        String encodedPassword = passwordEncoder.encode(dto.password());

        user.setFirstName(dto.firstName());
        user.setLastName(dto.lastName());
        user.setEmail( dto.email() );
        user.setPassword( dto.password() );
        user.setRole(Role.USER);
        user.setPassword(encodedPassword);
        user.setActive(true);


        return user;
    }

    public UserResponse toResponse(User entity) {
        if ( entity == null ) {
            return null;
        }

        Long id = null;
        String email = null;
        Role role = null;
        LocalDateTime createdAt = null;
        Boolean active = null;

        id = entity.getId();
        email = entity.getEmail();
        role = entity.getRole();
        createdAt = entity.getCreatedAt();
        active = entity.getActive();

        return new UserResponse( id, email, role, createdAt, active );
    }
}
