package com.devicehive.client.model;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@AllArgsConstructor
@RequiredArgsConstructor
public class NetworkFilter {

    private String name;
    private String namePattern;
    private SortField sortField = SortField.ID;
    private SortOrder sortOrder = SortOrder.ASC;
    private int take = 20;
    private int skip = 0;

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String namePattern() {
        return namePattern;
    }

    public void setNamePattern(String namePattern) {
        this.namePattern = namePattern;
    }

    public void setSortField(SortField sortField) {
        this.sortField = sortField;
    }


    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String sortOrder() {
        return sortOrder.sortOrder();
    }

    public String sortField() {
        return sortField.sortField();
    }

    public int take() {
        return take;
    }

    public void setTake(int take) {
        this.take = take;
    }

    public int skip() {
        return skip;
    }

    public void setSkip(int skip) {
        this.skip = skip;
    }
}
