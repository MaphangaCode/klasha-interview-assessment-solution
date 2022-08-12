package com.solutions.assessment.interview.klasha.controller;

import com.solutions.assessment.interview.klasha.domain.dto.GenericPagedResponseDto;
import com.solutions.assessment.interview.klasha.domain.dto.location.*;
import com.solutions.assessment.interview.klasha.service.LocationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/location/")
public class LocationController {

    private final LocationService locationService;

    public LocationController(final LocationService locationService) {
        this.locationService = locationService;
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

    @GetMapping("optimal-route/{originLocationId}/{destinationLocationId}")
    public ResponseEntity<OptimalRouteDto> getOptimalRoute(@PathVariable final Long originLocationId,
                                                           @PathVariable final Long destinationLocationId) {
        final OptimalRouteDto optimalRouteDto = new OptimalRouteDto();
        return new ResponseEntity<>(optimalRouteDto, HttpStatus.OK);
    }

}
