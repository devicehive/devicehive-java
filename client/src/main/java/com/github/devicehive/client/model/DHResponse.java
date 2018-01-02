/*
 *
 *
 *   DHResponse.java
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

public class DHResponse<T> {

    private T data;
    private FailureData failureData;

    public DHResponse(T data, FailureData failureData) {
        this.data = data;
        this.failureData = failureData;
    }

    public boolean isSuccessful() {
        return failureData == null;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public FailureData getFailureData() {
        return failureData;
    }

    public void setFailureData(FailureData failureData) {
        this.failureData = failureData;
    }

    public static <T> DHResponse<T> create(T data, FailureData failureData) {
        return new DHResponse<>(data, failureData);
    }

    @Override
    public String toString() {
        return "DHResponse{" +
                "data=" + data +
                ", failureData=" + failureData +
                '}';
    }
}
