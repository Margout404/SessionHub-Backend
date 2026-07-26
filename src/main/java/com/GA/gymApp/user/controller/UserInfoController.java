package com.GA.gymApp.user.controller;

import com.GA.gymApp.user.dto.UserInfo;
import com.GA.gymApp.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserInfoController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserInfo> getCurrentUser(
            Authentication authentication
    ) {
        UserInfo user =
                userService.currentUser(authentication.getName());

        return ResponseEntity.ok(user);
    }

}
