/*
 *
 *
 *   HiveClientException.java
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
 * Exception that occurred in case of user's mistake (for example, the field is null when it is required to be not
 * null). See <a href="http://www.devicehive.com/restful">DeviceHive RESTful API</a> for more details
 */
public class HiveClientException extends HiveException {

    private static final long serialVersionUID = -3831035988767839952L;

    public HiveClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public HiveClientException(String message) {
        super(message);
    }

    public HiveClientException(String message, Integer code) {
        super(message, code);
    }

    public HiveClientException(String message, Throwable cause, Integer code) {
        super(message, cause, code);
    }
}
