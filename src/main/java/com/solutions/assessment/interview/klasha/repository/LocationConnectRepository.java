package com.solutions.assessment.interview.klasha.repository;

import com.solutions.assessment.interview.klasha.domain.entity.LocationConnect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationConnectRepository extends JpaRepository<LocationConnect, Long> {
}
