package com.solutions.assessment.interview.klasha.domain.dto.location;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class OptimalRouteDto {
    private BigDecimal totalCost;
    private List<String> optimalRoute;
}
