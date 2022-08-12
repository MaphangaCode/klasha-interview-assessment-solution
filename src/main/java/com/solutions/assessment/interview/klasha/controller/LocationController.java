package com.solutions.assessment.interview.klasha.controller;

import com.solutions.assessment.interview.klasha.domain.dto.GenericPagedResponseDto;
import com.solutions.assessment.interview.klasha.domain.dto.LocationWarehouseDto;
import com.solutions.assessment.interview.klasha.domain.dto.ShortestPathDto;
import com.solutions.assessment.interview.klasha.domain.dto.location.*;
import com.solutions.assessment.interview.klasha.service.LocationService;
import com.solutions.assessment.interview.klasha.service.LocationWarehouseService;
import com.solutions.assessment.interview.klasha.service.ShortPathFinderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/location/")
public class LocationController {

    private final LocationService locationService;
    private final ShortPathFinderService shortPathFinderService;

    private final LocationWarehouseService locationWarehouseService;

    public LocationController(final LocationService locationService,
                              final ShortPathFinderService shortPathFinderService,
                              final LocationWarehouseService locationWarehouseService) {
        this.locationService = locationService;
        this.shortPathFinderService = shortPathFinderService;
        this.locationWarehouseService = locationWarehouseService;
    }

    @PostMapping("add")
    public ResponseEntity<AddLocationResponseDto> addLocation(@RequestBody final AddLocationDto addLocationDto) {

        final AddLocationResponseDto addLocationResponseDto = locationService.addLocation(addLocationDto);

        return new ResponseEntity<>(addLocationResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable final Long id) {

        final LocationDto locationDto = locationService.getLocation(id);

        return new ResponseEntity<>(locationDto, HttpStatus.OK);
    }

    @GetMapping("get")
    public ResponseEntity<GenericPagedResponseDto<LocationDto>> getLocations(@RequestParam(required = false) final Integer pageNumber,
                                                                             @RequestParam(required = false) final Integer pageSize) {
        final GenericPagedResponseDto<LocationDto> genericPagedResponseDto = locationService
                .retrieveLocations(pageNumber, pageSize);

        return new ResponseEntity<>(genericPagedResponseDto, HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<LocationDto> updateLocation(@RequestBody final UpdateLocationDto updateLocationDto) {
        final LocationDto locationDto = locationService.updateLocation(updateLocationDto);

        return new ResponseEntity<>(locationDto, HttpStatus.OK);
    }

    @GetMapping("shortest-route/{originLocationId}/{destinationLocationId}")
    public ResponseEntity<ShortestPathDto> getShortestRoute(@PathVariable final Long originLocationId,
                                                            @PathVariable final Long destinationLocationId) {
        final ShortestPathDto shortestPathDto = shortPathFinderService.getShortestPath(originLocationId, destinationLocationId);

        return new ResponseEntity<>(shortestPathDto, HttpStatus.OK);
    }

    @PostMapping("connect/{originLocationId}")
    public ResponseEntity<LocationDto> connectLocation(@PathVariable final Long originLocationId,
                                                       @RequestBody final List<Long> destinations) {
        final LocationDto locationDto = locationService.connectLocation(originLocationId, destinations);

        return new ResponseEntity<>(locationDto, HttpStatus.OK);
    }

    @DeleteMapping("remove/{locationId}")
    public void removeLocation(@PathVariable final Long locationId) {
        locationService.removeLocation(locationId);
    }


    @PostMapping("add-packages/{locationId}/{packageCount}")
    public ResponseEntity<LocationWarehouseDto> registerPackages(@PathVariable final Long locationId,
                                                                 @PathVariable final Integer packageCount) {
        final LocationWarehouseDto locationWarehouseDto = locationWarehouseService.addPackages(locationId, packageCount);

        return new ResponseEntity<>(locationWarehouseDto, HttpStatus.OK);
    }

}
