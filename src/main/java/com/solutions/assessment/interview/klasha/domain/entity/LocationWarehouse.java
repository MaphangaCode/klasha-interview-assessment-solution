package com.solutions.assessment.interview.klasha.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOCATION_WAREHOUSE")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationWarehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATION_WAREHOUSE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LOCATION_ID")
    private Location location;

    @Column(name = "AVAIL_COUNT", nullable = false)
    private Integer availCount;

    @Column(name = "CREATED_AT",nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;
}
