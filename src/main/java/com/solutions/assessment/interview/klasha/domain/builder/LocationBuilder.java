package com.solutions.assessment.interview.klasha.domain.builder;

import com.solutions.assessment.interview.klasha.domain.entity.Location;
import com.solutions.assessment.interview.klasha.domain.entity.LocationConnect;

import java.time.LocalDateTime;
import java.util.List;

public class LocationBuilder {
    private final Location location = new Location();

    public LocationBuilder setId(final Long id) {
        this.location.setId(id);
        return this;
    }

    public LocationBuilder setLongitude(final Double longitude) {
        this.location.setLongitude(longitude);
        return this;
    }

    public LocationBuilder setLatitude(final Double latitude) {
        this.location.setLatitude(latitude);
        return this;
    }

    public LocationBuilder setName(final String name) {
        this.location.setName(name);
        return this;
    }

    public LocationBuilder setConnectedWithLocationList(final List<LocationConnect> connectedWithLocationList) {
        this.location.setConnectedWithLocationList(connectedWithLocationList);
        return this;
    }

    public LocationBuilder setCreatedAt(final LocalDateTime createdAt) {
        this.location.setCreatedAt(createdAt);
        return this;
    }

    public LocationBuilder setDeletedAt(final LocalDateTime deletedAt) {
        this.location.setDeletedAt(deletedAt);
        return this;
    }

    public Location build() {
        return this.location;
    }

}
