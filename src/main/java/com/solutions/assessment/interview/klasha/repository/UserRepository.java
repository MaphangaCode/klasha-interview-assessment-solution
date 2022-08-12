package com.solutions.assessment.interview.klasha.repository;

import com.solutions.assessment.interview.klasha.domain.entity.LocationUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<LocationUser, Long> {

    LocationUser findByEmailAndPasswordAndDeletedAtIsNull(String email, String password);

    LocationUser findByEmailAndDeletedAtIsNull(String email);
}
