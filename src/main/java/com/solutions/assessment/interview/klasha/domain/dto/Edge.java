package com.solutions.assessment.interview.klasha.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Edge {
  private final Long id;
  private final Node sourceNode;
  private final Node destinationNode;
  private final Double distance;
}
