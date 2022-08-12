package com.solutions.assessment.interview.klasha.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "LOCATION")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATION_ID")
    private Long id;

    @Column(name = "LONGITUDE", nullable = false)
    private Double longitude;

    @Column(name = "LATITUDE", nullable = false)
    private Double latitude;

    @Column(name = "NAME")
    private String name;

    @OneToMany
    private List<LocationConnect> connectedWithLocationList;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "DELETED_AT")
    private LocalDateTime deletedAt;
}
