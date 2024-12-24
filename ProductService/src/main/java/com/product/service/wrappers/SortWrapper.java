package com.product.service.wrappers;

import org.springframework.data.domain.Sort;
import java.io.Serializable;

public class SortWrapper implements Serializable {

    private String sortProperty;
    private boolean ascending;

    public SortWrapper() {}

    public SortWrapper(String sortProperty, boolean ascending) {
        this.sortProperty = sortProperty;
        this.ascending = ascending;
    }

    public String getSortProperty() {
        return sortProperty;
    }

    public void setSortProperty(String sortProperty) {
        this.sortProperty = sortProperty;
    }

    public boolean isAscending() {
        return ascending;
    }

    public void setAscending(boolean ascending) {
        this.ascending = ascending;
    }

    public Sort toSort() {
        if (sortProperty == null || sortProperty.isEmpty()) {
            return Sort.unsorted();
        }
        return ascending ? Sort.by(sortProperty).ascending() : Sort.by(sortProperty).descending();
    }

    public static SortWrapper fromSort(Sort sort) {
        if (sort == null || sort.isEmpty() ) {
            return new SortWrapper();
        }
        String sortProperty = sort.iterator().next().getProperty();
        boolean ascending = sort.iterator().next().getDirection().isAscending();
        return new SortWrapper(sortProperty, ascending);
    }
}
