package com.devicehive.client.model;

public enum SortOrder {
    ASC("ASC"), DESC("DESC");
    private String sortOrder;

    SortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String sortOrder() {
        return sortOrder;
    }
}
