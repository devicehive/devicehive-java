/*
 *
 *
 *   HiveServerException.java
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

package com.github.devicehive.rest.model.exceptions;

/**
 * This kind of exceptions used when some server error occur: response with internal server error occured or on
 * connection lost event happened
 */
public class HiveServerException extends HiveException {

    private static final String MESSAGE = "Server error";
    private static final long serialVersionUID = 8781352790323264003L;

    public HiveServerException(Integer code) {
        super(MESSAGE, code);
    }

    public HiveServerException(String message, Integer code) {
        super(message, code);
    }
}
