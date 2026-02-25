package com.spring_study.chatting.controller;

import com.spring_study.chatting.domain.User;
import com.spring_study.chatting.dto.request.LocalSignupRequestDto;
import com.spring_study.chatting.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<Long> createLocalUser(@Valid @RequestBody LocalSignupRequestDto localSignupRequestDto) {
        return ResponseEntity.ok(userService.createLocalUser(localSignupRequestDto));
    }


}
