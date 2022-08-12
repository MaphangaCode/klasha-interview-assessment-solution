package com.solutions.assessment.interview.klasha.service.impl;

import com.solutions.assessment.interview.klasha.domain.dto.GraphDto;
import com.solutions.assessment.interview.klasha.domain.dto.ShortestPathDto;
import com.solutions.assessment.interview.klasha.domain.entity.LocationConnect;
import com.solutions.assessment.interview.klasha.domain.entity.LocationWarehouse;
import com.solutions.assessment.interview.klasha.domain.exception.ResourceInvalidStateException;
import com.solutions.assessment.interview.klasha.domain.exception.ResourceNotFoundException;
import com.solutions.assessment.interview.klasha.service.LocationService;
import com.solutions.assessment.interview.klasha.service.LocationWarehouseService;
import com.solutions.assessment.interview.klasha.service.ShortPathFinderService;
import com.solutions.assessment.interview.klasha.service.TranslatorService;
import com.solutions.assessment.interview.klasha.util.DijkstraShortPathImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShortPathFinderServiceImpl implements ShortPathFinderService {

    private final TranslatorService translatorService;
    private final LocationService locationService;

    private final LocationWarehouseService locationWarehouseService;

    @Autowired
    public ShortPathFinderServiceImpl(final TranslatorService translatorService,
                                      final LocationService locationService,
                                      final LocationWarehouseService locationWarehouseService) {
        this.translatorService = translatorService;
        this.locationService = locationService;
        this.locationWarehouseService = locationWarehouseService;
    }

    @Override
    public ShortestPathDto getShortestPath(final Long sourceLocationId,
                                           final Long destinationLocationId) {

        final LocationWarehouse locationWarehouse = locationWarehouseService
                .getLocationWarehouse(sourceLocationId);

        if (locationWarehouse.getAvailCount() < 3) {
            throw new ResourceInvalidStateException("Source warehouse needs to have a minimum of three packages");
        }

        final List<LocationConnect> locationConnectList = locationService
                .getShortPathSourceAndDestLocationConnect(sourceLocationId, destinationLocationId);

        final List<Long> locationIdList = new ArrayList<>();

        for (LocationConnect locationConnect: locationConnectList) {
            locationIdList.add(locationConnect.getLocationA().getId());
            locationIdList.add(locationConnect.getLocationB().getId());
        }

        if (!(locationIdList.contains(sourceLocationId) && locationIdList.contains(destinationLocationId))) {
            throw new ResourceNotFoundException("Missing location for calculation");
        }

        final GraphDto graphDto = translatorService.resolveGraph(locationConnectList);
        final DijkstraShortPathImpl dijkstraShortPath = new DijkstraShortPathImpl(graphDto);
       return dijkstraShortPath.findShortestPath(sourceLocationId);
    }
}
