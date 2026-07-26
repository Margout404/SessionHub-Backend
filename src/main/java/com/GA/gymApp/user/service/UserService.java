package com.GA.gymApp.user.service;

import com.GA.gymApp.exceptions.Exceptions;
import com.GA.gymApp.security.generic.JwtService;
import com.GA.gymApp.user.dto.*;
import com.GA.gymApp.user.mapper.UserMapper;
import com.GA.gymApp.user.model.User;
import com.GA.gymApp.user.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public UserResponse register(UserCreateDTO request){

        User user = mapper.toEntity(request);

        Optional<User> exist= repository.findByEmail(request.email());

        if (exist.isPresent()){
            throw new Exceptions.ConflictException("User already exists");
        }


            repository.save(user);

        return mapper.toResponse(user);
    }

    public LoginResponse login(LoginRequest request){


        User user = userRepository.findByEmail(request.email()).orElseThrow(()->new Exceptions.AuthorizationException("Incorrect Email or password"));

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.email(),
                            request.password()
                    )
            );
        } catch (AuthenticationException e) {
            throw new Exceptions.AuthorizationException("Incorrect Email or password");
        }


        String token = jwtService.generateToken(user);

        return new LoginResponse(token);

    }

    public BookingResponseDto makeBooking(Long trainingSessionId){
//        TODO
        return null;
    }

    public UserInfo currentUser(String email){
        User user = userRepository.findByEmail(email).orElseThrow(()-> new Exceptions.AuthorizationException("No user with this email : "+ email));

        return new UserInfo(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getRole());
    }


}
