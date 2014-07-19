package com.devicehive.websockets.util;

import com.devicehive.json.GsonFactory;
import com.devicehive.util.LogExecutionTime;
import com.devicehive.websockets.HiveWebsocketSessionState;
import com.google.gson.JsonElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.*;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.inject.Qualifier;
import javax.websocket.Session;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.ConcurrentLinkedQueue;


@EJB(beanInterface = AsyncMessageSupplier.class, name = AsyncMessageSupplier.NAME)
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class AsyncMessageSupplier {

    private static final Logger logger = LoggerFactory.getLogger(AsyncMessageSupplier.class);

    public static final String NAME = "java:global/DeviceHive/AsyncMessageSupplier";

    private static final int RETRY_COUNT = 3;
    private static final int RETRY_DELAY = 10;


    @EJB
    private AsyncMessageSupplier self;


    @Asynchronous
    public void deliverMessages(@Observes @Delivery Session session) {
        this.deliverMessages(session, 0);
    }

    @Asynchronous
    public void deliverMessages(Session session, int tryCount) {
        try {
            doDeliverMessages(session);
        } catch (IOException ex) {
            logger.error("Error message delivery, session id is {} ", session.getId());
            if (tryCount <= RETRY_COUNT) {
                logger.info("Retry in {} seconds", RETRY_DELAY);
                self.deliverMessages(session, tryCount + 1);
            } else {
                logger.info("No more tries");
            }
        }
    }

    @LogExecutionTime
    private void doDeliverMessages(Session session) throws IOException {
        @SuppressWarnings("unchecked")
        ConcurrentLinkedQueue<JsonElement> queue = HiveWebsocketSessionState.get(session).getQueue();
        boolean acquired = false;
//        do {
            try {
                acquired = HiveWebsocketSessionState.get(session).getQueueLock().tryLock();
                if (acquired) {
                    while (!queue.isEmpty()) {
                        JsonElement jsonElement = queue.peek();
                        if (jsonElement == null) {
                            queue.poll();
                            continue;
                        }
                        if (session.isOpen()) {
                            String data = GsonFactory.createGson().toJson(jsonElement);
                            session.getBasicRemote().sendText(data);
                            queue.poll();
                        } else {
                            logger.error("Session is closed. Unable to deliver message");
                            queue.clear();
                            return;
                        }
                        logger.debug("Session {}: {} messages left", session.getId(), queue.size());
                    }
                } //else {
//                    return;
//                }
            } finally {
                if (acquired) {
                    HiveWebsocketSessionState.get(session).getQueueLock().unlock();
                }
            }
//        } while (!queue.isEmpty());
    }

    @Qualifier
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE})
    public @interface Delivery {

    }
}



