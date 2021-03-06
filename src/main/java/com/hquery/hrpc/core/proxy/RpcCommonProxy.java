package com.hquery.hrpc.core.proxy;

import com.hquery.hrpc.constants.GlobalConstants;
import com.hquery.hrpc.core.connector.RpcConnector;
import com.hquery.hrpc.core.model.RpcRequest;
import com.hquery.hrpc.core.model.RpcResponse;
import com.hquery.hrpc.utils.SnowflakeIdWorker;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;


/**
 * @author Administrator
 */
public class RpcCommonProxy implements RpcProxy {

    private SnowflakeIdWorker idWorker = new SnowflakeIdWorker(GlobalConstants.WORKER_ID, GlobalConstants.DATA_CENTER_ID);

    private RpcConnector rpcConnector;

    public RpcCommonProxy(RpcConnector rpcConnector) {
        this.rpcConnector = rpcConnector;
    }

    @Override
    public Object getProxy(Class<?> clazz) {
        InvocationHandler invocationHandler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                RpcRequest request = new RpcRequest();
                request.setRequestId(idWorker.nextId());
                request.setClassName(clazz.getName());
                request.setMethodName(method.getName());
                request.setParameterTypes(method.getParameterTypes());
                request.setParameters(args);
                RpcResponse response = rpcConnector.invoke(request);
                if (response == null) {
                    return null;
                }
                return response.getResult();
            }
        };
        return Proxy.newProxyInstance(RpcCommonProxy.class.getClassLoader(), new Class[]{clazz}, invocationHandler);
    }

}
