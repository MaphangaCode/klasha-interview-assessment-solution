package com.solutions.assessment.interview.klasha.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class GenericPagedResponseDto<E> {
    private List<E> data;
    private Boolean hasNext;
    private Boolean hasPrevious;
    private Integer pageNumber;
    private Integer pageSize;
    private Integer totPages;
    private Long totElements;
}
