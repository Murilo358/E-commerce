package com.product.service.wrappers;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import com.product.service.wrappers.SortWrapper;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class PageableWrapper implements Serializable {

    private Object content;
    private int pageNumber;
    private int pageSize;
    private SortWrapper sort;

    public PageableWrapper() {
    }

    public PageableWrapper(Object content, int pageNumber, int pageSize, SortWrapper sort) {
        this.content = content;
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sort = sort;
    }

    public static PageableWrapper fromPageable(Pageable pageable) {
        return new PageableWrapper(
                null,
                pageable.getPageNumber(),
                pageable.getPageSize(),
                SortWrapper.fromSort(pageable.getSort())
        );
    }

    public static PageableWrapper fromPage(Page<?> page) {
        return new PageableWrapper(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                SortWrapper.fromSort(page.getSort())
        );
    }

    public Pageable toPageable() {
        return PageRequest.of(pageNumber, pageSize, sort != null ? sort.toSort() : Sort.unsorted());
    }
}
