package com.jexhsu.rpc.config;

import com.jexhsu.rpc.loadbalancer.LoadBalancer;
import com.jexhsu.rpc.loadbalancer.LoadBalancerKeys;
import com.jexhsu.rpc.serializer.SerializerKeys;
import lombok.Data;

/**
 * RPC 框架配置
 */
@Data
public class RpcConfig {

    /**
     * 名称
     */
    private String name = "jex-rpc";

    /**
     * 版本号
     */
    private String version = "2.0";

    /**
     * 服务器主机名
     */
    private String serverHost = "localhost";

    /**
     * 服务器端口号
     */
    private Integer serverPort = 1234;

    /**
     * 模拟调用
     */
    private boolean mock = false;

    /**
     * 序列化器
     */
    private String serializer = SerializerKeys.HESSIAN;

    /**
     * 注册中心配置
     */
    private RegistryConfig registryConfig = new RegistryConfig();

    /**
     * 复杂均衡器
     */
    private String loadBalancer = LoadBalancerKeys.ROUND_ROBIN;

}
