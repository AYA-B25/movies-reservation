package com.example.backend.controllers;

import com.example.backend.dtos.UserResponse;
import com.example.backend.entities.User;
import com.example.backend.mappers.UserMapper;
import com.example.backend.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class userController {

    private final UserMapper userMapper;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(@AuthenticationPrincipal UserDetailsImpl userDetails){

        User user = userDetails.getUser();
        UserResponse userResponse = userMapper.toResponse(user);

        return ResponseEntity.ok(userResponse);
    }

}
