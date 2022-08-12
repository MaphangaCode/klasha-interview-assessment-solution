package com.solutions.assessment.interview.klasha.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterUserDto {
    private String email;
    private String firstName;
    private String surname;
    private String password;
}
