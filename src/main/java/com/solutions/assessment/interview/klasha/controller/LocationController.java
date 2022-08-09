package com.solutions.assessment.interview.klasha.controller;

import com.solutions.assessment.interview.klasha.domain.dto.location.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/location/")
public class LocationController {

    @PostMapping("add/location")
    public ResponseEntity<AddLocationResponseDto> addLocation(@RequestBody final AddLocationDto addLocationDto) {

        final AddLocationResponseDto addLocationResponseDto = new AddLocationResponseDto();

        return new ResponseEntity<>(addLocationResponseDto, HttpStatus.CREATED);
    }

    @GetMapping("get/{id}")
    public ResponseEntity<LocationDto> getLocation(@PathVariable final Long id) {

        final LocationDto locationDto = new LocationDto();

        return new ResponseEntity<>(locationDto, HttpStatus.OK);
    }

    @GetMapping("get")
    public ResponseEntity<List<LocationDto>> getLocations(@RequestParam final Integer pageNumber,
                                                          @RequestParam final Integer pageSize) {
        final List<LocationDto> locationDtoList = new ArrayList<>();

        return new ResponseEntity<>(locationDtoList, HttpStatus.OK);
    }

    @PutMapping("update")
    public ResponseEntity<LocationDto> updateLocation(@RequestParam final UpdateLocationDto updateLocationDto) {
        final LocationDto locationDto = new LocationDto();

        return new ResponseEntity<>(locationDto, HttpStatus.OK);
    }

    @GetMapping("optimal-route/{originLocationId}/{destinationLocationId}")
    public ResponseEntity<OptimalRouteDto> getOptimalRoute(@PathVariable final Long originLocationId,
                                                           @PathVariable final Long destinationLocationId) {
        final OptimalRouteDto optimalRouteDto = new OptimalRouteDto();
        return new ResponseEntity<>(optimalRouteDto, HttpStatus.OK);
    }

}
