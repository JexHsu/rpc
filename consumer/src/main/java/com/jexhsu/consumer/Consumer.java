package com.jexhsu.consumer;

import com.jexhsu.common.model.User;
import com.jexhsu.common.service.UserService;
import com.jexhsu.rpc.bootstrap.ConsumerBootstrap;
import com.jexhsu.rpc.config.RpcConfig;
import com.jexhsu.rpc.proxy.ServiceProxyFactory;
import com.jexhsu.rpc.utils.ConfigUtils;

/**
 * Simple service consumer example.
 */
public class Consumer {

    public static void main(String[] args) {
        // 服务提供者初始化
        ConsumerBootstrap.init();

        // 获取代理
        UserService userService = ServiceProxyFactory.getProxy(UserService.class);

        // 调用
        User user = new User();
        user.setName("jexhsu");
        String res = userService.thumbs_up(user);
        if (res != null) {
            System.out.println("Result: " + res);
        } else {
            System.out.println("error: Result is null.");
        }
    }
}
