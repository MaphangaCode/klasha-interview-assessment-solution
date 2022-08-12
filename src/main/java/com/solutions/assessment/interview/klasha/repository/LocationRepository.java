package com.solutions.assessment.interview.klasha.repository;

import com.solutions.assessment.interview.klasha.domain.entity.Location;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    @Query(value = " select * from LOCATION as l " +
            " left join " +
            " ( " +
            "   select * from LOCATION_CONNECT ls" +
            "   where ls.DELETED_AT is null " +
            "   and ls.LOCATION_A_ID = :id " +
            " ) as lca " +
            " on l.LOCATION_ID = lca.LOCATION_A_ID" +
            " left join " +
            " ( " +
            "   select * from LOCATION_CONNECT ls " +
            "   where ls.DELETED_AT is null " +
            "   and ls.LOCATION_B_ID = :id " +
            " ) as lcb " +
            " on l.LOCATION_ID = lcb.LOCATION_B_ID " +
            " where l.LOCATION_ID = :id " +
            " and l.DELETED_AT is null ",
            nativeQuery = true )
    Location findByIdAndDeletedAtIsNull(Long id);

    @Query(value = " select * from LOCATION as l " +
            " left join " +
            " ( " +
            "   select * from LOCATION_CONNECT ls" +
            "   where ls.DELETED_AT is null " +
            "   and ls.LOCATION_A_ID in (:locationIdList) " +
            " ) as lca " +
            " on l.LOCATION_ID = lca.LOCATION_A_ID" +
            " left join " +
            " ( " +
            "   select * from LOCATION_CONNECT ls " +
            "   where ls.DELETED_AT is null " +
            "   and ls.LOCATION_B_ID in (:locationIdList) " +
            " ) as lcb " +
            " on l.LOCATION_ID = lcb.LOCATION_B_ID " +
            " where l.LOCATION_ID in (:locationIdList) " +
            " and l.DELETED_AT is null ",
            nativeQuery = true )
    List<Location> findByIdInAndDeletedAtIsNull(List<Long> locationIdList);

    @Query(value = " select * from LOCATION as l " +
            " left join " +
            " ( " +
            "   select * from LOCATION_CONNECT ls" +
            "   where ls.DELETED_AT is null " +
            " ) as lca " +
            " on l.LOCATION_ID = lca.LOCATION_A_ID" +
            " left join " +
            " ( " +
            "   select * from LOCATION_CONNECT ls " +
            "   where ls.DELETED_AT is null " +
            " ) as lcb " +
            " on l.LOCATION_ID = lcb.LOCATION_B_ID " +
            " where l.DELETED_AT is null ",
            nativeQuery = true )
    Page<Location> findByDeletedAtIsNull(Pageable pageable);

    Location findByLongitudeAndLatitudeAndDeletedAtIsNull(Double longitude, Double latitude);

}
