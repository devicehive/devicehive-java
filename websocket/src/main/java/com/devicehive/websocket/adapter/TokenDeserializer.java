package com.devicehive.websocket.adapter;

import com.devicehive.websocket.model.repsonse.ErrorAction;
import com.devicehive.websocket.model.repsonse.JwtTokenResponse;
import com.devicehive.websocket.model.repsonse.TokenResponse;
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
            JwtTokenResponse tokenVO = gson.fromJson(jsonObject, JwtTokenResponse.class);
            return new TokenResponse(tokenVO, null);
        }
    }
}
