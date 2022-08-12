package com.solutions.assessment.interview.klasha.repository;

import com.solutions.assessment.interview.klasha.domain.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    Location findByIdAndDeletedAtIsNull(Long id);

    List<Location> findAllByIdInAndDeletedAtIsNull(List<Long> locationIdList);

    Page<Location> findByDeletedAtIsNull(Pageable pageable);

    List<Location> findByDeletedAtIsNull();

    Location findByLongitudeAndLatitudeAndDeletedAtIsNull(Double longitude, Double latitude);

}
