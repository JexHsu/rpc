package com.jexhsu.provider;


import com.jexhsu.common.service.UserService;
import com.jexhsu.rpc.RpcApplication;
import com.jexhsu.rpc.config.RegistryConfig;
import com.jexhsu.rpc.config.RpcConfig;
import com.jexhsu.rpc.model.ServiceMetaInfo;
import com.jexhsu.rpc.registry.LocalRegistry;
import com.jexhsu.rpc.registry.Registry;
import com.jexhsu.rpc.registry.RegistryFactory;
import com.jexhsu.rpc.server.HttpServer;
import com.jexhsu.rpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 */
public class Provider {

    public static void main(String[] args) {
        // RPC 框架初始化
        RpcApplication.init();

        // 注册服务
        String serviceName = UserService.class.getName();
        LocalRegistry.register(serviceName, UserServiceImpl.class);

        // 注册服务到注册中心
        RpcConfig rpcConfig = RpcApplication.getRpcConfig();
        RegistryConfig registryConfig = rpcConfig.getRegistryConfig();
        Registry registry = RegistryFactory.getInstance(registryConfig.getRegistry());
        ServiceMetaInfo serviceMetaInfo = new ServiceMetaInfo();
        serviceMetaInfo.setServiceName(serviceName);
        serviceMetaInfo.setServiceHost(rpcConfig.getServerHost());
        serviceMetaInfo.setServicePort(rpcConfig.getServerPort());
        try {
            registry.register(serviceMetaInfo);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(RpcApplication.getRpcConfig().getServerPort());
    }
}
