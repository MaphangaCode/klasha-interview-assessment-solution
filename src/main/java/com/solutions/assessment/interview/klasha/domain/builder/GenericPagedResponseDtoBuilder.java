package com.solutions.assessment.interview.klasha.domain.builder;

import com.solutions.assessment.interview.klasha.domain.dto.GenericPagedResponseDto;

import java.util.List;

public class GenericPagedResponseDtoBuilder<E> {
    private final GenericPagedResponseDto<E> genericPagedResponseDto = new GenericPagedResponseDto<>();

    public GenericPagedResponseDtoBuilder<E> setData(final List<E> data) {
        this.genericPagedResponseDto.setData(data);
        return this;
    }

    public GenericPagedResponseDtoBuilder<E> setHasNext(final Boolean hasNext) {
        this.genericPagedResponseDto.setHasNext(hasNext);
        return this;
    }

    public GenericPagedResponseDtoBuilder<E> setHasPrevious(final Boolean hasPrevious) {
        this.genericPagedResponseDto.setHasPrevious(hasPrevious);
        return this;
    }

    public GenericPagedResponseDtoBuilder<E> setPageNumber(final Integer pageNumber) {
        this.genericPagedResponseDto.setPageNumber(pageNumber);
        return this;
    }

    public GenericPagedResponseDtoBuilder<E> setPageSize(final Integer pageSize) {
        this.genericPagedResponseDto.setPageSize(pageSize);
        return this;
    }

    public GenericPagedResponseDtoBuilder<E> setTotPages(final Integer totPages) {
        this.genericPagedResponseDto.setTotPages(totPages);
        return this;
    }

    public GenericPagedResponseDtoBuilder<E> setTotElements(final Long totElements) {
        this.genericPagedResponseDto.setTotElements(totElements);
        return this;
    }

    public GenericPagedResponseDto<E> build() {
        return this.genericPagedResponseDto;
    }

}
