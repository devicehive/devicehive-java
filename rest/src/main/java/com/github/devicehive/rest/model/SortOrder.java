/*
 *
 *
 *   SortOrder.java
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

package com.github.devicehive.rest.model;

import com.google.gson.TypeAdapter;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
@JsonAdapter(SortOrder.Adapter.class)
public enum SortOrder {
    ASC("ASC"), DESC("DESC");
    private String sortOrder;

    SortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String sortOrder() {
        return sortOrder;
    }

    @Override
    public String toString() {
        return sortOrder;
    }

    public static SortOrder fromValue(String text) {
        for (SortOrder b : SortOrder.values()) {
            if (String.valueOf(b.sortOrder).equals(text)) {
                return b;
            }
        }
        return null;
    }

    public static class Adapter extends TypeAdapter<SortOrder> {
        @Override
        public void write(final JsonWriter jsonWriter, final SortOrder enumeration) throws IOException {
            jsonWriter.value(enumeration.sortOrder());
        }

        @Override
        public SortOrder read(final JsonReader jsonReader) throws IOException {
            Integer value = jsonReader.nextInt();
            return SortOrder.fromValue(String.valueOf(value));
        }
    }
}
