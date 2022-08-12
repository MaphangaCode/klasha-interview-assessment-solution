package com.solutions.assessment.interview.klasha.repository;

import com.solutions.assessment.interview.klasha.domain.entity.LocationWarehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationWarehouseRepository extends JpaRepository<LocationWarehouse, Long> {
    LocationWarehouse findByLocationIdAndDeletedAtIsNull(Long locationId);
}
