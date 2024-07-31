package com.jexhsu.rpc.loadbalancer;

import com.jexhsu.rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 随机负载均衡器
 */
public class Random implements LoadBalancer {

    private final java.util.Random random = new java.util.Random();

    @Override
    public ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList) {
        int size = serviceMetaInfoList.size();
        if (size == 0) {
            return null;
        }
        // 只有 1 个服务，不用随机
        if (size == 1) {
            return serviceMetaInfoList.get(0);
        }
        return serviceMetaInfoList.get(random.nextInt(size));
    }
}
