package com.jexhsu.provider;


import com.jexhsu.common.service.UserService;
import com.jexhsu.rpc.registry.LocalRegistry;
import com.jexhsu.rpc.server.HttpServer;
import com.jexhsu.rpc.server.VertxHttpServer;

/**
 * 简易服务提供者示例
 */
public class BasicProvider {

    public static void main(String[] args) {
        // 注册服务
        LocalRegistry.register(UserService.class.getName(), UserServiceImpl.class);

        // 启动 web 服务
        HttpServer httpServer = new VertxHttpServer();
        httpServer.doStart(1234);
    }
}
