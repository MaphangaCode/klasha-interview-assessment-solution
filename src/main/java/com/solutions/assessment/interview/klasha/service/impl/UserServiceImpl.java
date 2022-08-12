package com.solutions.assessment.interview.klasha.service.impl;

import com.solutions.assessment.interview.klasha.domain.builder.UserBuilder;
import com.solutions.assessment.interview.klasha.domain.dto.RegisterUserDto;
import com.solutions.assessment.interview.klasha.domain.entity.LocationUser;
import com.solutions.assessment.interview.klasha.domain.exception.ResourceInvalidStateException;
import com.solutions.assessment.interview.klasha.repository.UserRepository;
import com.solutions.assessment.interview.klasha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserServiceImpl(final UserRepository userRepository,
                           final PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    @Override
    public Long registerUser(final RegisterUserDto registerUserDto) {
        final LocationUser existingUser = userRepository.findByEmailAndDeletedAtIsNull(registerUserDto.getEmail()) ;

        if (existingUser != null) {
            throw new ResourceInvalidStateException("User already exist");
        }

         LocationUser newUser = new UserBuilder()
                 .setFirstName(registerUserDto.getFirstName())
                 .setSurname(registerUserDto.getSurname())
                 .setEmail(registerUserDto.getEmail())
                 .setPassword(encoder.encode(registerUserDto.getPassword()))
                 .setCreatedAt(LocalDateTime.now())
                 .build();

        newUser = userRepository.save(newUser);

        return newUser.getId();
    }
}
