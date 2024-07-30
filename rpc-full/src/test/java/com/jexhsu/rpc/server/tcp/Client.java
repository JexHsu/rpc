package com.jexhsu.rpc.server.tcp;

import io.vertx.core.Vertx;

public class Client {
    public static void start(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();
        vertx.createNetClient().connect(port, "localhost", result -> {
            if (result.succeeded()) {
                System.out.println("Connected to TCP server");
                io.vertx.core.net.NetSocket socket = result.result();
                for (int i = 0; i < 1000; i++) {
                    // 发送数据
                    socket.write("you are my sunshine, my only sunshine. you make me happy, when skies are gray.");
                }
                // 接收响应
                socket.handler(buffer -> {
                    System.out.println("Received response from server: " + buffer.toString());
                });
            } else {
                System.err.println("Failed to connect to TCP server");
            }
        });
    }

    // 测试半包和粘包问题
    public static void main(String[] args) {
       start(1234);
    }
}
