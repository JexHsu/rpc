package com.jexhsu.consumer;

import com.jexhsu.common.model.User;
import com.jexhsu.common.service.UserService;
import com.jexhsu.rpc.config.RpcConfig;
import com.jexhsu.rpc.proxy.ServiceProxyFactory;
import com.jexhsu.rpc.utils.ConfigUtils;

/**
 * Simple service consumer example.
 */
public class Consumer {

    public static void main(String[] args) {
        RpcConfig rpc = ConfigUtils.loadConfig(RpcConfig.class, "rpc");
        System.out.println(rpc);

        // Dynamic proxy instance
        UserService dynamicProxy = ServiceProxyFactory.getProxy(UserService.class);

        User user = new User();
        user.setName("jexhsu");

        String res = dynamicProxy.thumbs_up(user);
        if (res != null) {
            System.out.println("Result: " + res);
        } else {
            System.out.println("error: Result is null.");
        }

        // if rpc.mock=true
//        short number = dynamicProxy.getNumber();
//        System.out.println("the number is " + number);
    }
}
