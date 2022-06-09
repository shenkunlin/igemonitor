package com.itheima.ige.rpc.client.runner;

import com.itheima.ige.rpc.data.Response;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public class SyncRequestFuture implements RequestFuture<Response> {

    // 请求回调缓存
    public static Map<String, RequestFuture> syncRequest = new ConcurrentHashMap<String, RequestFuture>();
    // 计数器
    private CountDownLatch latch = new CountDownLatch(1);
    // 标记开始时间， 判断是否超时
    private final long begin = System.currentTimeMillis();
    // 超时时间设定
    private long timeout;
    // rpc响应对象
    private Response response;
    // 请求ID
    private final String requestId;
    // 标记是否有回调结果
    private boolean writeResult;
    // 调用异常记录
    private Throwable cause;
    // 标记调用是否超时
    private boolean isTimeout = false;

    public SyncRequestFuture(String requestId) {
        this.requestId = requestId;
    }

    /**
     * 构造方法
     * @param requestId
     * @param timeout
     */
    public SyncRequestFuture(String requestId, long timeout) {
        this.requestId = requestId;
        this.timeout = timeout;
        writeResult = true;
        isTimeout = false;
    }

    /**
     * 获取异常栈信息
     * @return
     */
    public Throwable cause() {
        return cause;
    }

    /**
     * 设置异常栈信息
     * @param cause
     */
    public void setCause(Throwable cause) {
        this.cause = cause;
    }

    /**
     * 标记是否成功接收到回调结果
     * @return
     */
    public boolean isWriteSuccess() {
        return writeResult;
    }

    /**
     * 标记回调结果
     * @param result
     */
    public void setWriteResult(boolean result) {
        this.writeResult = result;
    }

    /**
     * 获取请求ID
     * @return
     */
    public String reqId() {
        return requestId;
    }

    /**
     * 获取响应结果
     * @return
     */
    public Response response() {
        return response;
    }

    /**
     * 设置响应结果信息
     * @param response
     */
    public void setResponse(Response response) {
        this.response = response;
        latch.countDown();
    }

    /**
     * 取消调用
     * @param mayInterruptIfRunning
     * @return
     */
    public boolean cancel(boolean mayInterruptIfRunning) {
        return true;
    }

    /**
     * 标记是否取消
     * @return
     */
    public boolean isCancelled() {
        return false;
    }

    /**
     * 标记是否完成
     * @return
     */
    public boolean isDone() {
        return false;
    }

    /**
     * 获取响应结果（阻塞式）
     * @return
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public Response get() throws InterruptedException, ExecutionException {
        latch.wait();
        return response;
    }

    /**
     * 获取响应结果（指定阻塞等待时间）
     * @param timeout
     * @param unit
     * @return
     */
    public Response get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException {
        if (latch.await(timeout, unit)) {
            return response;
        }
        return null;
    }

    /**
     * 标记请求调用是否超时
     * @return
     */
    public boolean isTimeout() {
        if (isTimeout) {
            return isTimeout;
        }
        return System.currentTimeMillis() - begin > timeout;
    }
}