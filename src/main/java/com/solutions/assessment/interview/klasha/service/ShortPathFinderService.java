package com.solutions.assessment.interview.klasha.service;

import com.solutions.assessment.interview.klasha.domain.dto.ShortestPathDto;

public interface ShortPathFinderService {

    ShortestPathDto getShortestPath(Long sourceLocationId,
                                    Long destinationLocationId);
}
