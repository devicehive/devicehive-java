package com.devicehive.client.model;

import org.joda.time.DateTime;

import java.util.Arrays;
import java.util.List;

public class CommandFilter {

    public List<String> getCommandNames() {
        return commandNames;
    }

    public void setCommandNames(String... commandNames) {
        this.commandNames = Arrays.asList(commandNames);
    }

    private List<String> commandNames;
    private DateTime startTimestamp;
    private DateTime endTimestamp;
    private SortOrder sortOrder;
    private SortField sortField;
    private int maxNumber;


    public SortOrder getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(SortOrder sortOrder) {
        this.sortOrder = sortOrder;
    }

    public SortField getSortField() {
        return sortField;
    }

    public void setSortField(SortField sortField) {
        this.sortField = sortField;
    }


    public DateTime getStartTimestamp() {
        return startTimestamp.withMillis(0);
    }

    public void setStartTimestamp(DateTime startTimestamp) {
        this.startTimestamp = startTimestamp;
    }

    public DateTime getEndTimestamp() {
        return endTimestamp;
    }

    public void setEndTimestamp(DateTime endTimestamp) {
        this.endTimestamp = endTimestamp;
    }

    public int getMaxNumber() {
        return maxNumber;
    }

    public void setMaxNumber(int maxNumber) {
        this.maxNumber = maxNumber;
    }
}
