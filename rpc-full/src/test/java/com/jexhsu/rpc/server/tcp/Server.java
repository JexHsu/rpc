package com.jexhsu.rpc.server.tcp;

import com.jexhsu.rpc.server.HttpServer;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.net.NetServer;
import io.vertx.core.parsetools.RecordParser;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Server implements HttpServer {
    @Override
    public void doStart(int port) {
        // 创建 Vert.x 实例
        Vertx vertx = Vertx.vertx();

        // 创建 TCP 服务器
        NetServer server = vertx.createNetServer();

        // 处理请求
        server.connectHandler(socket -> {
            String msg = "you are my sunshine, my only sunshine. you make me happy, when skies are gray.";
            int len = msg.getBytes().length;
            // 展示半包粘包问题
//            Handler<Buffer> bufferHandler = buffer -> {
//                if (buffer.getBytes().length < len) {
//                    System.out.println("half packet, length = " + buffer.getBytes().length);
//                    return;
//                }
//                if (buffer.getBytes().length > len) {
//                    System.out.println("sticky packet, length = " + buffer.getBytes().length);
//                    return;
//                }
//                String str = new String(buffer.getBytes(0, len));
//                System.out.println(str);
//                if (msg.equals(str)) {
//                    System.out.println("perfect!!!");
//                }
//            };
//
//            socket.handler(bufferHandler);

            // 构造parser 解决半包粘包问题
            RecordParser parse = RecordParser.newFixed(len);
            parse.setOutput(new Handler<Buffer>() {
                @Override
                public void handle(Buffer buffer) {
                    String str = new String(buffer.getBytes());
                    System.out.println(str);
                    if (msg.equals(str)) {
                        System.out.println("perfetc!!!");
                    }
                }
            });

            socket.handler(parse);
        });

        // 启动 TCP 服务器并监听指定端口
        server.listen(port, result -> {
            if (result.succeeded()) {
                log.info("TCP server started on port " + port);
            } else {
                log.info("Failed to start TCP server: " + result.cause());
            }
        });
    }

    // 测试半包和粘包问题
    public static void main(String[] args) {
        new Server().doStart(1234);
    }

}
