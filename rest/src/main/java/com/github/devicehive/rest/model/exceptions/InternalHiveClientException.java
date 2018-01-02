/*
 *
 *
 *   InternalHiveClientException.java
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
 * Unexpected exception. Should not occur. If occurred, that means that library is used with incompatible version of
 * device hive server API.
 */
public class InternalHiveClientException extends HiveClientException {

    private static final long serialVersionUID = -8333329700032097189L;

    public InternalHiveClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public InternalHiveClientException(String message) {
        super(message);
    }

    public InternalHiveClientException(String message, Integer code) {
        super(message, code);
    }
}
