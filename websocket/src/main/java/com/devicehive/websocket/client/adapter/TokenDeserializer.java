package com.devicehive.websocket.client.adapter;

import com.devicehive.websocket.client.model.ErrorAction;
import com.devicehive.websocket.client.model.JwtTokenVO;
import com.devicehive.websocket.client.model.TokenResponse;
import com.google.gson.*;

import java.lang.reflect.Type;

public class TokenDeserializer implements JsonDeserializer<TokenResponse> {

    @Override
    public TokenResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement element = jsonObject.get("status");
        String status = element.getAsString();
        Gson gson = new Gson();
        if (status.equalsIgnoreCase("error")) {
            ErrorAction error = gson.fromJson(jsonObject, ErrorAction.class);
            return new TokenResponse(null, error);
        } else {
            JwtTokenVO tokenVO = gson.fromJson(jsonObject, JwtTokenVO.class);
            return new TokenResponse(tokenVO, null);
        }
    }
}
