package com.solutions.assessment.interview.klasha.controller;

import com.solutions.assessment.interview.klasha.domain.dto.RegisterUserDto;
import com.solutions.assessment.interview.klasha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user/")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(final UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    private ResponseEntity<Long> register(@RequestBody final RegisterUserDto registerUserDto) {
        return ResponseEntity.ok(userService.registerUser(registerUserDto));
    }
}
