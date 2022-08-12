package com.solutions.assessment.interview.klasha.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class LocationWarehouseDto {
    private Long locationId;
    private Long warehouseId;
    private Integer packageCount;
}
