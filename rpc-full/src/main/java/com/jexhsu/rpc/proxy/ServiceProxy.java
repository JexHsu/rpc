package com.jexhsu.rpc.proxy;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.jexhsu.rpc.RpcApplication;
import com.jexhsu.rpc.config.RpcConfig;
import com.jexhsu.rpc.constant.RpcConstant;
import com.jexhsu.rpc.fault.retry.RetryStrategy;
import com.jexhsu.rpc.fault.retry.RetryStrategyFactory;
import com.jexhsu.rpc.loadbalancer.LoadBalancer;
import com.jexhsu.rpc.loadbalancer.LoadBalancerFactory;
import com.jexhsu.rpc.model.RpcRequest;
import com.jexhsu.rpc.model.RpcResponse;
import com.jexhsu.rpc.model.ServiceMetaInfo;
import com.jexhsu.rpc.protocol.*;
import com.jexhsu.rpc.registry.Registry;
import com.jexhsu.rpc.registry.RegistryFactory;
import com.jexhsu.rpc.server.tcp.VertxTcpClient;
import com.jexhsu.rpc.serializer.JdkSerializer;
import com.jexhsu.rpc.serializer.Serializer;
import com.jexhsu.rpc.serializer.SerializerFactory;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetClient;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

/**
 * 服务代理（JDK 动态代理）
 */
public class ServiceProxy implements InvocationHandler {

    /**
     * 调用代理
     *
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 指定序列化器

//        // 1. 硬编码序列化器实例
//        final Serializer serializer = new JdkSerializer();
        // 2. 从自定义 SPI 工厂获取序列化器
        final Serializer serializer = SerializerFactory
                .getInstance(RpcApplication.getRpcConfig()
                        .getSerializer());
//        // 3. 使用 Java 内置 SPI 机制获取序列化器
//        final Serializer serializer = SerializerFactory.getSerializer();

        // 构造请求
        String serviceName = method.getDeclaringClass().getName();
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .parameterTypes(method.getParameterTypes())
                .args(args)
                .build();
        try {
            // 序列化
            byte[] bodyBytes = serializer.serialize(rpcRequest);

            // 从注册中心获取服务提供者请求地址
            RpcConfig rpcConfig = RpcApplication.getRpcConfig();
            Registry registry = RegistryFactory.getInstance(rpcConfig.getRegistryConfig().getRegistry());
            ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
            serviceMetaInfo.setServiceName(serviceName);
            serviceMetaInfo.setServiceVersion(RpcConstant.DEFAULT_SERVICE_VERSION);
            List<ServiceMetaInfo> serviceMetaInfoList = registry.serviceDiscovery(serviceMetaInfo.getServiceKey());
            if (CollUtil.isEmpty(serviceMetaInfoList)) {
                throw new RuntimeException("暂无服务地址");
            }

            // 负载均衡
            LoadBalancer loadBalancer = LoadBalancerFactory.getInstance(rpcConfig.getLoadBalancer());
            // 将调用方法名（请求路径）作为负载均衡参数
            Map<String, Object> requestParams = new HashMap<>();
            requestParams.put("methodName", rpcRequest.getMethodName());
            ServiceMetaInfo selectedServiceMetaInfo = loadBalancer.select(requestParams, serviceMetaInfoList);

            // 发送 TCP 请求
            // 使用重试机制
            RetryStrategy retryStrategy = RetryStrategyFactory.getInstance(rpcConfig.getRetryStrategy());
            RpcResponse rpcResponse = retryStrategy.doRetry(() ->
                    VertxTcpClient.doRequest(rpcRequest, selectedServiceMetaInfo)
            );
            return rpcResponse.getData();
            // 发送 http 请求
//            try (HttpResponse httpResponse = HttpRequest.post(selectedServiceMetaInfo.getServiceAddress())
//                    .body(bodyBytes)
//                    .execute()) {
//                byte[] result = httpResponse.bodyBytes();
//                // 反序列化
//                RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
//                return rpcResponse.getData();
//            }
        } catch (IOException e) {
            throw new RuntimeException("调用失败");
        }
    }
}
