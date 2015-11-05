package com.devicehive.client;


import com.devicehive.client.impl.HiveClientRestImpl;
import com.devicehive.client.impl.HiveClientWebsocketImpl;
import com.devicehive.client.impl.context.RestAgent;
import com.devicehive.client.impl.context.WebsocketAgent;
import com.devicehive.client.model.ApiInfo;
import com.devicehive.client.model.exceptions.HiveException;

import java.net.URI;

public class HiveFactory {

    private HiveFactory() {
    }


    public static HiveClient createClient(URI restUri, boolean preferWebsockets) throws HiveException {
        if (preferWebsockets) {
            return new HiveClientWebsocketImpl(
                createWebsocketClientAgent(restUri));
        } else {
            return new HiveClientRestImpl(createRestAgent(restUri));
        }
    }



    private static RestAgent createRestAgent(URI restUri) throws HiveException {
        RestAgent agent = new RestAgent(restUri);
        agent.connect();
        return agent;
    }

    private static WebsocketAgent createWebsocketClientAgent(URI restUri)
        throws HiveException {
        WebsocketAgent
            agent =
            new WebsocketAgent(restUri);
        agent.connect();
        return agent;
    }

    public static void main(String... args) throws HiveException {
        HiveClient hiveClient = createClient(URI.create("http://playground.devicehive.com/api/rest"), false);
        for (int i = 0; i < 1000; i++) {
            ApiInfo apiInfo = hiveClient.getInfo();
            System.out.println(apiInfo.getServerTimestamp());
        }

    }
}
