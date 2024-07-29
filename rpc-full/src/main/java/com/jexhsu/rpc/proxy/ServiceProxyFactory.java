package com.jexhsu.rpc.proxy;

import com.jexhsu.rpc.RpcApplication;
import com.jexhsu.rpc.server.MockServiceProxy;

import java.lang.reflect.Proxy;

/**
 * 服务代理工厂（用于创建代理对象）
 */
public class ServiceProxyFactory {

    /**
     * 根据服务类获取代理对象
     *
     * @param serviceClass 服务类
     * @param <T>          服务类的类型
     * @return 代理对象
     */
    public static <T> T getProxy(Class<T> serviceClass) {
        return createProxy(serviceClass, RpcApplication.getRpcConfig().isMock()
                ? new MockServiceProxy()
                : new ServiceProxy());
    }

    /**
     * 创建代理对象
     *
     * @param serviceClass 服务类
     * @param handler      代理处理器
     * @param <T>          服务类的类型
     * @return 代理对象
     */
    private static <T> T createProxy(Class<T> serviceClass, Object handler) {
        return (T) Proxy.newProxyInstance(
                serviceClass.getClassLoader(),
                new Class[]{serviceClass},
                (java.lang.reflect.InvocationHandler) handler);
    }
}
