/*
 *
 *
 *   NetworkFilter.java
 *
 *   Copyright (C) 2018 DataArt
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 *
 */

package com.github.devicehive.client.model;

import com.github.devicehive.rest.model.SortField;
import com.github.devicehive.rest.model.SortOrder;

public class NetworkFilter {

    private String name;
    private String namePattern;
    private SortField sortField = SortField.ID;
    private SortOrder sortOrder = SortOrder.ASC;
    private int take = 20;
    private int skip = 0;

    public NetworkFilter(String name, String namePattern, SortField sortField, SortOrder sortOrder, int take, int skip) {
        this.name = name;
        this.namePattern = namePattern;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
        this.take = take;
        this.skip = skip;
    }

    public NetworkFilter() {
    }

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
