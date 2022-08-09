package com.solutions.assessment.interview.klasha.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "LOCATION_CONNECT")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationConnect {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOCATION_CONNECT_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LOC_A", nullable = false)
    private Location locationA;

    @ManyToOne
    @JoinColumn(name = "LOC_B", nullable = false)
    private Location locationB;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "DELETED_AT", nullable = false)
    private LocalDateTime deletedAt;
}
