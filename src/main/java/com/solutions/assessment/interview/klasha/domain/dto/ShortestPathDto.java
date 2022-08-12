package com.solutions.assessment.interview.klasha.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ShortestPathDto {
    private Double totalCost;
    private List<String> shortestPath;
}
