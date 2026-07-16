package com.GA.gymApp.user.controller;


import com.GA.gymApp.user.dto.LoginRequest;
import com.GA.gymApp.user.dto.LoginResponse;
import com.GA.gymApp.user.dto.UserCreateDTO;
import com.GA.gymApp.user.dto.UserResponse;
import com.GA.gymApp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/register")
    public UserResponse register(@RequestBody UserCreateDTO dto){
        return userService.register(dto);
    }

    @PostMapping( "/login")
    public LoginResponse login(@RequestBody LoginRequest dto){
        return userService.login(dto);
    }
}
