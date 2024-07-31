package com.jexhsu.rpc.loadbalancer;

import com.jexhsu.rpc.loadbalancer.ConsistentHash;
import com.jexhsu.rpc.loadbalancer.LoadBalancer;
import com.jexhsu.rpc.loadbalancer.Random;
import com.jexhsu.rpc.loadbalancer.RoundRobin;
import com.jexhsu.rpc.model.ServiceMetaInfo;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

/**
 * 负载均衡器测试
 */
public class test {

    Map<String, Object> params = new HashMap<>();
    List<ServiceMetaInfo> list = new ArrayList<>();

    @Test
    public void select() {
        // 请求参数
        params.put("methodName", "ipad");
        // 服务列表
        ServiceMetaInfo serviceMetaInfo1 = new ServiceMetaInfo();
        serviceMetaInfo1.setServiceName("myService");
        serviceMetaInfo1.setServiceVersion("1.0");
        serviceMetaInfo1.setServiceHost("localhost");
        serviceMetaInfo1.setServicePort(1234);
        ServiceMetaInfo serviceMetaInfo2 = new ServiceMetaInfo();
        serviceMetaInfo2.setServiceName("myService");
        serviceMetaInfo2.setServiceVersion("1.0");
        serviceMetaInfo2.setServiceHost("jexhsu.com");
        serviceMetaInfo2.setServicePort(80);
        list = Arrays.asList(serviceMetaInfo1, serviceMetaInfo2);
        final LoadBalancer consistentHash = new ConsistentHash();
        final LoadBalancer random = new Random();
        final LoadBalancer roundRobin = new RoundRobin();
        // 连续调用 3 次
        invoke(consistentHash);
        invoke(random);
        invoke(roundRobin);
    }

    private void invoke(LoadBalancer loadBalancer) {
        System.out.printf("<<<<<<<<<<<<<<<<%s>>>>>>>>>>>>>>>\n", loadBalancer.getClass().getSimpleName());
        ServiceMetaInfo metaInfo = loadBalancer.select(params, list);
        System.out.println(metaInfo);
        Assert.assertNotNull(metaInfo);
        metaInfo = loadBalancer.select(params, list);
        System.out.println(metaInfo);
        Assert.assertNotNull(metaInfo);
        metaInfo = loadBalancer.select(params, list);
        System.out.println(metaInfo);
        Assert.assertNotNull(metaInfo);
    }

}
