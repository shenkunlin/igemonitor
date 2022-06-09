package com.itheima.ige.rpc.client.proxy;

import com.itheima.ige.rpc.data.Request;
import com.itheima.ige.rpc.data.Response;
import com.itheima.ige.rpc.util.IdWorker;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;
import org.springframework.beans.factory.FactoryBean;

import java.lang.reflect.Method;

public class ServiceFactory<T> implements FactoryBean<T> {

    private Class<T> interfaceType;
    public ServiceFactory(Class<T> interfaceType) {
        this.interfaceType = interfaceType;
    }

    @Override
    public T getObject() throws Exception {
        return newProxyInstance(this.interfaceType);
    }

    @Override
    public Class<?> getObjectType() {
        return this.interfaceType;
    }

    /**
     * 创建新的代理实例-CGLib动态代理
     * @param clazz
     * @param <T>
     * @return
     */
    public <T> T newProxyInstance(Class<T> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new ProxyCallBackHandler());
        return (T) enhancer.create();
    }


    /**
     * 代理拦截实现
     */
    class ProxyCallBackHandler implements MethodInterceptor {

        @Override
        public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
            return doIntercept(method, args);
        }

        /**
         * 拦截RCP接口调用
         * @param method
         * @param parameters
         * @return
         * @throws Throwable
         */
        private Object doIntercept(Method method, Object[] parameters) throws Throwable {
            //1、封装RpcRequest请求参数
            String requestId = IdWorker.nextStr(); //唯一会话ID
            String methodName = method.getName();
            String className = method.getDeclaringClass().getName();
            Class<?>[] parameterTypes = method.getParameterTypes();

            Request request = Request.builder()
                    .reqId(requestId)
                    .className(className)
                    .methodName(methodName)
                    .parameterTypes(parameterTypes)
                    .parameters(parameters)
                    .build();

            //2、发送数据
            Response respon = ProxyInvoker.sendRequest(request);

            //3、获取返回结果
            return respon.getResult();
        }
    }
}
