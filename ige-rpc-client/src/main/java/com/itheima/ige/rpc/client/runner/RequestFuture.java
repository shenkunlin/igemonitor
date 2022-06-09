package com.itheima.ige.rpc.client.runner;
import com.itheima.ige.rpc.data.Response;
import java.util.concurrent.Future;

public interface RequestFuture<T> extends Future<T> {

    Throwable cause();

    void setCause(Throwable cause);

    boolean isWriteSuccess();

    void setWriteResult(boolean result);

    String reqId();

    T response();

    void setResponse(Response response);

    boolean isTimeout();
}