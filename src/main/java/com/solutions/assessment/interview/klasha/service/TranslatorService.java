package com.solutions.assessment.interview.klasha.service;

import com.solutions.assessment.interview.klasha.domain.dto.GraphDto;
import com.solutions.assessment.interview.klasha.domain.entity.LocationConnect;

import java.util.List;

public interface TranslatorService {

    /**
     * We need to feed this service a list of connect locations and return a graph
     * */
    GraphDto resolveGraph(List<LocationConnect> locationConnectList);
}
