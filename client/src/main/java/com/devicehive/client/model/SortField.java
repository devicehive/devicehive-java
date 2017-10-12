package com.devicehive.client.model;

public enum SortField {
    ID("ID"), DESC("NAME");
    private String sortField;

    SortField(String sortField) {
        this.sortField = sortField;
    }

    public String sortField() {
        return sortField;
    }
}
