package com.solutions.assessment.interview.klasha.service;

import com.solutions.assessment.interview.klasha.domain.dto.RegisterUserDto;

public interface UserService {

    Long registerUser(RegisterUserDto registerUserDto);
}
