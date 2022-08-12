package com.solutions.assessment.interview.klasha.domain.builder;

import com.solutions.assessment.interview.klasha.domain.dto.location.LocationDto;

import java.util.List;

public class LocationDtoBuilder {
    private final LocationDto locationDto = new LocationDto();

    public LocationDtoBuilder setId(final Long id) {
        this.locationDto.setId(id);
        return this;
    }

    public LocationDtoBuilder setLongitude(final Double longitude) {
        this.locationDto.setLongitude(longitude);
        return this;
    }

    public LocationDtoBuilder setLatitude(final Double latitude) {
        this.locationDto.setLatitude(latitude);
        return this;
    }

    public LocationDtoBuilder setName(final String name) {
        this.locationDto.setName(name);
        return this;
    }

    public LocationDtoBuilder setConnectedWithLocationList(final List<LocationDto> connectedWithLocationList) {
        this.locationDto.setConnectedWithLocationList(connectedWithLocationList);
        return this;
    }

    public LocationDto build() {
        return locationDto;
    }
}
