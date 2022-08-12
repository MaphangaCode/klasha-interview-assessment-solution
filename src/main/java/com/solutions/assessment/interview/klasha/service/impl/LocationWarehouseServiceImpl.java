package com.solutions.assessment.interview.klasha.service.impl;

import com.solutions.assessment.interview.klasha.domain.dto.LocationWarehouseDto;
import com.solutions.assessment.interview.klasha.domain.entity.LocationWarehouse;
import com.solutions.assessment.interview.klasha.domain.exception.ResourceInvalidStateException;
import com.solutions.assessment.interview.klasha.repository.LocationWarehouseRepository;
import com.solutions.assessment.interview.klasha.service.LocationWarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LocationWarehouseServiceImpl implements LocationWarehouseService {

    private final LocationWarehouseRepository locationWarehouseRepository;

    @Autowired
    public LocationWarehouseServiceImpl(final LocationWarehouseRepository locationWarehouseRepository) {
        this.locationWarehouseRepository = locationWarehouseRepository;
    }

    @Override
    public LocationWarehouse getLocationWarehouse(final Long locationId) {
        final LocationWarehouse locationWarehouse = locationWarehouseRepository
                .findByLocationIdAndDeletedAtIsNull(locationId);

        if (locationWarehouse == null) {
            throw new ResourceInvalidStateException("Could not find warehouse for source location");
        }

        return locationWarehouse;
    }

    @Override
    public void deleteLocationWarehouse(final Long locationId) {
        final LocationWarehouse locationWarehouse = getLocationWarehouse(locationId);

        locationWarehouse.setDeletedAt(LocalDateTime.now());
        locationWarehouseRepository.save(locationWarehouse);
    }

    @Override
    public LocationWarehouseDto addPackages(final Long locationId,
                                            final Integer packageCount) {
        LocationWarehouse locationWarehouse = getLocationWarehouse(locationId);
        locationWarehouse.setAvailCount(locationWarehouse.getAvailCount() + packageCount);
        locationWarehouse = locationWarehouseRepository.save(locationWarehouse);

        return new LocationWarehouseDto(locationId,
                locationWarehouse.getId(),
                locationWarehouse.getAvailCount());
    }
}
