package com.solutions.assessment.interview.klasha.repository;

import com.solutions.assessment.interview.klasha.domain.entity.LocationConnect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationConnectRepository extends JpaRepository<LocationConnect, Long> {

    List<LocationConnect> findAllBySourceLocationIdOrDestLocationIdAndDeletedAtIsNull(Long aId, Long bIg);

    Page<LocationConnect> findAllBySourceLocationDeletedAtIsNullOrDestLocationDeletedAtIsNullAndDeletedAtIsNull(Pageable pageable);
}
