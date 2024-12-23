package com.product.service.wrappers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.Serializable;

@Getter
@Setter
public class PageableWrapper implements Serializable {

    private int pageNumber;
    private int pageSize;
    private SortWrapper sort;

    public PageableWrapper() {}

    public PageableWrapper(int pageNumber, int pageSize, SortWrapper sort) {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public Pageable toPageable() {
        return PageRequest.of(pageNumber, pageSize, sort != null ? sort.toSort() : Sort.unsorted());
    }

    public static PageableWrapper fromPageable(Pageable pageable) {
        return new PageableWrapper(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                SortWrapper.fromSort(pageable.getSort())
        );
    }
}
