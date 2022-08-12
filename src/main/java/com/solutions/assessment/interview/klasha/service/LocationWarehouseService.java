package com.solutions.assessment.interview.klasha.service;

import com.solutions.assessment.interview.klasha.domain.dto.LocationWarehouseDto;
import com.solutions.assessment.interview.klasha.domain.entity.LocationWarehouse;

public interface LocationWarehouseService {

    LocationWarehouse getLocationWarehouse(Long locationId);

    void deleteLocationWarehouse(Long locationId);

    LocationWarehouseDto addPackages(Long locationId, Integer packageCount);
}
