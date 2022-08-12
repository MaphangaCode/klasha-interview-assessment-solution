package com.solutions.assessment.interview.klasha.service;

import com.solutions.assessment.interview.klasha.domain.dto.GenericPagedResponseDto;
import com.solutions.assessment.interview.klasha.domain.dto.location.AddLocationDto;
import com.solutions.assessment.interview.klasha.domain.dto.location.AddLocationResponseDto;
import com.solutions.assessment.interview.klasha.domain.dto.location.LocationDto;
import com.solutions.assessment.interview.klasha.domain.dto.location.UpdateLocationDto;
import com.solutions.assessment.interview.klasha.domain.entity.LocationConnect;

import java.util.List;

public interface LocationService {

    LocationDto getLocation(Long id);

    AddLocationResponseDto addLocation(AddLocationDto addLocationDto);

    GenericPagedResponseDto<LocationDto> retrieveLocations(Integer pageNumber, Integer pageSize);

    LocationDto updateLocation(UpdateLocationDto updateLocationDto);

    List<LocationConnect> getShortPathSourceAndDestLocationConnect(Long sourceLocationId,
                                                                   Long destLocationId);

    LocationDto connectLocation(Long originLocationId,
                                List<Long> destinationIdList);

    void removeLocation(Long locationId);
}
