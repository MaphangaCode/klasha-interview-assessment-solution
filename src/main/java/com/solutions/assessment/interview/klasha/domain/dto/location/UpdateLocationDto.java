package com.solutions.assessment.interview.klasha.domain.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UpdateLocationDto {

    private Long id;
    private Double updatedLongitude;
    private Double updatedLatitude;
    private String updatedName;
    private List<Long> addedConnectedLocationList;
    private List<Long> removedConnectedLocationList;
}
