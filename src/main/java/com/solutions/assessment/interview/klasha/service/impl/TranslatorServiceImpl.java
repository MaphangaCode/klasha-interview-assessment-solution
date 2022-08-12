package com.solutions.assessment.interview.klasha.service.impl;

import com.solutions.assessment.interview.klasha.domain.dto.Edge;
import com.solutions.assessment.interview.klasha.domain.dto.GraphDto;
import com.solutions.assessment.interview.klasha.domain.dto.Node;
import com.solutions.assessment.interview.klasha.domain.entity.Location;
import com.solutions.assessment.interview.klasha.domain.entity.LocationConnect;
import com.solutions.assessment.interview.klasha.service.TranslatorService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.apache.lucene.util.SloppyMath.haversinMeters;

@Service
public class TranslatorServiceImpl implements TranslatorService {

    @Override
    public GraphDto resolveGraph(final List<LocationConnect> locationConnectList) {
        final List<Node> nodes = new ArrayList<>();

        final List<Edge> edges = locationConnectList.stream()
                .map(locationConnect -> {
                    final Location sourceLocation = locationConnect.getSourceLocation();
                    final Location destinationLocation = locationConnect.getDestLocation();

                    final Node sourceNode  = new Node(sourceLocation.getId(), sourceLocation.getName());

                    final Node destinationNode = new Node(destinationLocation.getId(),
                            destinationLocation.getName());

                    nodes.add(sourceNode);
                    nodes.add(destinationNode);

                    final Double distance = (haversinMeters(sourceLocation.getLatitude(),
                            sourceLocation.getLongitude(), destinationLocation.getLatitude(),
                            destinationLocation.getLongitude())) / 1000.0;
                    return new Edge(locationConnect.getId(), sourceNode, destinationNode, distance);
                }).collect(Collectors.toList());

        return new GraphDto(nodes, edges);
    }
}
