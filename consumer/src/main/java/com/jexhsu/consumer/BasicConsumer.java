package com.jexhsu.consumer;

import com.jexhsu.common.model.User;
import com.jexhsu.common.service.UserService;
import com.jexhsu.rpc.proxy.ServiceProxyFactory;

import java.lang.reflect.Method;

/**
 * 简易服务消费者示例
 */
public class BasicConsumer {

    public static void main(String[] args) {
        // 静态代理
        UserService userService = new UserServiceProxy();

//        // 动态代理
//        UserService userService = ServiceProxyFactory.getProxy(UserService.class);

        User user = new User();
        user.setName("jexhsu");
        // 调用
        String str = userService.thumbs_up(user);
        if (str != null) {
            System.out.println(str);
        } else {
            System.out.println("user == null");
        }
    }
}
