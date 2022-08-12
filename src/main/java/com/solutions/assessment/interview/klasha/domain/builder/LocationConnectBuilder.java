package com.solutions.assessment.interview.klasha.domain.builder;

import com.solutions.assessment.interview.klasha.domain.entity.Location;
import com.solutions.assessment.interview.klasha.domain.entity.LocationConnect;

import java.time.LocalDateTime;

public class LocationConnectBuilder {
    private final LocationConnect locationConnect = new LocationConnect();

    public LocationConnectBuilder setId(final Long id) {
        this.locationConnect.setId(id);
        return this;
    }

    public LocationConnectBuilder setLocationA(final Location locationA) {
        this.locationConnect.setSourceLocation(locationA);
        return this;
    }

    public LocationConnectBuilder setLocationB(final Location locationB) {
        this.locationConnect.setDestLocation(locationB);
        return this;
    }

    public LocationConnectBuilder setCreatedAt(final LocalDateTime createdAt) {
        this.locationConnect.setCreatedAt(createdAt);
        return this;
    }

    public LocationConnectBuilder setDeletedAt(final LocalDateTime deletedAt) {
        this.locationConnect.setDeletedAt(deletedAt);
        return this;
    }

    public LocationConnect build() {
        return this.locationConnect;
    }
}
