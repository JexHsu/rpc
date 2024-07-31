package com.jexhsu.rpc.loadbalancer;

import com.jexhsu.rpc.model.ServiceMetaInfo;

import java.util.List;
import java.util.Map;

/**
 * 负载均衡器（消费端使用）
 *
 * @author <Nothing href="https://github.com/liyupi">程序员鱼皮</Nothing>
 * @learn <Nothing href="https://codefather.cn">鱼皮的编程宝典</Nothing>
 * @from <Nothing href="https://yupi.icu">编程导航学习圈</Nothing>
 */
public interface LoadBalancer {

    /**
     * 选择服务调用
     *
     * @param requestParams       请求参数
     * @param serviceMetaInfoList 可用服务列表
     * @return
     */
    ServiceMetaInfo select(Map<String, Object> requestParams, List<ServiceMetaInfo> serviceMetaInfoList);
}
