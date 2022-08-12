package com.solutions.assessment.interview.klasha.domain.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateLocationDto {

    private Long id;
    private Double updatedLongitude;
    private Double updatedLatitude;
    private String updatedName;
}
