package com.jexhsu.consumer;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.jexhsu.common.model.User;
import com.jexhsu.common.service.UserService;
import com.jexhsu.rpc.model.RpcRequest;
import com.jexhsu.rpc.model.RpcResponse;
import com.jexhsu.rpc.serializer.JdkSerializer;
import com.jexhsu.rpc.serializer.Serializer;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 静态代理
 */
public class UserServiceProxy implements UserService {

    public String thumbs_up(User user) {
        // 指定序列化器
        Serializer serializer = new JdkSerializer();

        Method[] methods = UserService.class.getDeclaredMethods();
        String method = methods[0].getName(); // thumbs_up

        // 发请求
        RpcRequest rpcRequest = RpcRequest.builder()
                .serviceName(UserService.class.getName())
                .methodName(method)
                .parameterTypes(new Class[]{User.class})
                .args(new Object[]{user})
                .build();
        try {
            byte[] bodyBytes = serializer.serialize(rpcRequest);
            byte[] result;
            try (HttpResponse httpResponse = HttpRequest.post("http://localhost:1234")
                    .body(bodyBytes)
                    .execute()) {
                result = httpResponse.bodyBytes();
            }
            RpcResponse rpcResponse = serializer.deserialize(result, RpcResponse.class);
            return (String) rpcResponse.getData();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
