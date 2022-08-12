package com.solutions.assessment.interview.klasha.domain.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationDto {
   private Long id;
   private Double longitude;
   private Double latitude;
   private String name;
   private List<LocationDto> connectedWithLocationList;
}
