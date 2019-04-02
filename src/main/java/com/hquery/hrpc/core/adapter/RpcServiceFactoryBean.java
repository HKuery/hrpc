package com.hquery.hrpc.core.adapter;

import com.hquery.hrpc.core.proxy.RpcProxy;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.lang.Nullable;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author hquery.huang
 * 2019/4/2 16:09:18
 */
public class RpcServiceFactoryBean<T> implements FactoryBean<T> {

    private Class serviceInterface;

    private RpcProxy proxy;

    /**
     * 缓存构建的代理对象
     *
     * @param null
     * @return
     * @author hquery
     * 2019/4/2 18:27:30
     */
    private static final ConcurrentHashMap<Class, Object> OBJECT_CONTAINER = new ConcurrentHashMap<>();

    public RpcServiceFactoryBean(RpcProxy proxy, Class serviceInterface) {
        this.proxy = proxy;
        this.serviceInterface = serviceInterface;
    }

    @Nullable
    @Override
    public T getObject() throws Exception {
        Object object = OBJECT_CONTAINER.get(this.serviceInterface);
        if (object != null) {
            return (T) object;
        }
        return (T) createObject();
    }

    private synchronized Object createObject() {
        Object object = OBJECT_CONTAINER.get(this.serviceInterface);
        if (object != null) {
            return object;
        }
        // 构建代理对象
        Object proxyObject = proxy.getProxy(this.serviceInterface);
        OBJECT_CONTAINER.put(this.serviceInterface, proxyObject);
        return proxyObject;
    }

    @Nullable
    @Override
    public Class<?> getObjectType() {
        return this.serviceInterface;
    }
}
