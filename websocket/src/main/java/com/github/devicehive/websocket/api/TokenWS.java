/*
 *
 *
 *   TokenWS.java
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

package com.github.devicehive.websocket.api;

import com.github.devicehive.rest.model.JwtPayload;
import com.github.devicehive.websocket.listener.TokenListener;
import com.github.devicehive.websocket.model.repsonse.ResponseAction;
import com.github.devicehive.websocket.model.repsonse.TokenGetResponse;
import com.github.devicehive.websocket.model.repsonse.TokenRefreshResponse;
import com.github.devicehive.websocket.model.request.TokenCreateAction;
import com.github.devicehive.websocket.model.request.TokenGetAction;

import static com.github.devicehive.websocket.model.ActionConstant.*;

public class TokenWS extends BaseWebSocketApi implements TokenApi {
    static final String TAG = "token";
    private TokenListener tokenListener;

    TokenWS(WebSocketClient client) {
        super(client, null);
    }

    public void setListener(TokenListener listener) {
        super.setListener(listener);
        this.tokenListener = listener;
    }

    @Override
    public String getKey() {
        return TAG;
    }

    @Override
    public void onSuccess(String message) {
        ResponseAction action = getResponseAction(message);
        String actionName = action.getAction();
        if (actionName.equalsIgnoreCase(TOKEN_GET)) {
            TokenGetResponse tokenVO = gson.fromJson(message, TokenGetResponse.class);
            tokenListener.onGet(tokenVO);
        } else if (actionName.equalsIgnoreCase(TOKEN_REFRESH)) {
            TokenRefreshResponse tokenVO = gson.fromJson(message, TokenRefreshResponse.class);
            tokenListener.onRefresh(tokenVO);

        } else if (actionName.equalsIgnoreCase(TOKEN_CREATE)) {
            TokenGetResponse tokenVO = gson.fromJson(message, TokenGetResponse.class);
            tokenListener.onCreate(tokenVO);

        }
    }

    @Override
    public void get(Long requestId, String login, String password) {
        TokenGetAction action = new TokenGetAction();
        action.setLogin(login);
        action.setPassword(password);
        action.setRequestId(requestId);
        send(action);
    }

    @Override
    public void create(Long requestId, JwtPayload payload) {
        TokenCreateAction action = new TokenCreateAction();
        action.setRequestId(requestId);
        action.setPayload(payload);
        send(action);
    }

    @Override
    public void refresh(Long requestId, String refreshToken) {
        com.github.devicehive.websocket.model.request.TokenRefreshAction action = new com.github.devicehive.websocket.model.request.TokenRefreshAction();
        action.setRefreshToken(refreshToken);
        action.setRequestId(requestId);
        send(action);
    }

}
