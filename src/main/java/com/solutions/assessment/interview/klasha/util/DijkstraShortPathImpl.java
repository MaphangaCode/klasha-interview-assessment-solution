package com.solutions.assessment.interview.klasha.util;

import com.solutions.assessment.interview.klasha.domain.dto.Edge;
import com.solutions.assessment.interview.klasha.domain.dto.GraphDto;
import com.solutions.assessment.interview.klasha.domain.dto.Node;
import com.solutions.assessment.interview.klasha.domain.dto.ShortestPathDto;
import com.solutions.assessment.interview.klasha.domain.exception.ResourceInvalidStateException;
import com.solutions.assessment.interview.klasha.domain.exception.ResourceNotFoundException;

import java.util.*;
import java.util.stream.Collectors;

public class DijkstraShortPathImpl {
    private final List<Node> nodes;
    private final List<Edge> edges;
    private Set<Node> settledNodes;
    private Set<Node> unsettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Double> distance;


    public DijkstraShortPathImpl(final GraphDto graphDto) {
        nodes = new ArrayList<>(graphDto.getNodes());
        edges = new ArrayList<>(graphDto.getEdges());
    }

    private Node getNodeById(final Long nodeId) {
        return nodes.stream()
                .filter(node -> node.getId().equals(nodeId))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Failed to resolve source node by ID"));
    }

    public ShortestPathDto findShortestPath(final Long sourceNodeId) {
        final Node sourceNode = getNodeById(sourceNodeId);
        settledNodes = new HashSet<>();
        unsettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(sourceNode, (double) 0);
        unsettledNodes.add(sourceNode);

        while(unsettledNodes.size() > 0) {
            final Node node = getMinimum(unsettledNodes);
            settledNodes.add(node);
            unsettledNodes.remove(node);
            computeMinimumDistances(node);
        }

        return getPath(sourceNode);
    }

    private Double getDistanceBetweenTwoNodes(final Long sourceId, final Long destinationId) {
        return edges.stream()
                .filter(edge ->  edge.getSourceNode().getId().equals(sourceId) &&
                        edge.getDestinationNode().getId().equals(destinationId))
                .findAny()
                .orElseThrow(() -> new ResourceNotFoundException("Failed to resolve distance between two points"))
                .getDistance();
    }

    private ShortestPathDto getPath(final Node searchNode) {
        final LinkedList<Node> path = new LinkedList<>();
        Node counterNode = searchNode;

        if (predecessors.get(counterNode) == null) {
            throw new ResourceInvalidStateException("Could not calculate shortest path");
        }

        path.add(counterNode);

        while (predecessors.get(counterNode) != null) {
            counterNode = predecessors.get(counterNode);
            path.add(counterNode);
        }

        Double totDistance = 0.0;
        for (int i = 0; i < path.size() - 1; i++) {
            totDistance += getDistanceBetweenTwoNodes(path.get(i).getId(),
                    path.get(i + 1).getId());
        }

        final Double totCost = 1.00 * totDistance;
        return new ShortestPathDto(totCost, path.stream().map(Node::getName).collect(Collectors.toList()));
    }

    private Boolean isSettle(final Node node) {
        return settledNodes.contains(node);
    }

    private Double getShortestDistance(final Node destinationNode) {
        final Double destinationDistance = distance.get(destinationNode);

        if (destinationDistance == null) {
            return Double.MAX_VALUE;
        }
        return destinationDistance;
    }

    private Node getMinimum(final Set<Node> nodes) {
        Node minimum = null;

        for (Node node : nodes) {
            if (minimum ==  null) {
                minimum = node;
            } else {
                if (getShortestDistance(node) < getShortestDistance(minimum)) {
                    minimum = node;
                }
            }
        }
        return minimum;
    }

    private List<Node> getNeighbours(final Node node) {
        final List<Node> neighbours = new ArrayList<>();

        for (final Edge edge : edges) {
            if (edge.getSourceNode().getId().equals(node.getId()) &&
                    !isSettle(edge.getDestinationNode())) {
                neighbours.add(edge.getDestinationNode());
            }
        }

        return neighbours;
    }

    private Double getDistance(final Node node, final Node target) {
        for (final Edge edge : edges) {
            if (edge.getSourceNode().getId().equals(node.getId())
                    && edge.getDestinationNode().getId().equals(target.getId())) {
                return edge.getDistance();
            }
        }

        throw new ResourceInvalidStateException("Invalid get distance operation");
    }

    private void computeMinimumDistances(final Node node) {
        final List<Node> adjacentNodes = getNeighbours(node);

        for (final Node target: adjacentNodes) {
            if(getShortestDistance(target) > getShortestDistance(node) + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node) + getDistance(node, target));
                predecessors.put(node, target);
                unsettledNodes.add(target);
            }
        }
    }
}
