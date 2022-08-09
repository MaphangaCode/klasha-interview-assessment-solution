package com.solutions.assessment.interview.klasha.domain.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddLocationDto {
    private String name;
    private Double longitude;
    private Double latitude;
}
