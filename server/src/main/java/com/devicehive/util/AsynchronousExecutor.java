package com.devicehive.util;

import javax.ejb.*;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

@Stateless
@Local
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class AsynchronousExecutor {

    @Asynchronous
    public void execute(Runnable runnable) {
        runnable.run();
    }

    @Asynchronous
    public <V> Future<V> execute(Callable<V> callable) throws Exception {
        return new AsyncResult<V>(callable.call());
    }
}
