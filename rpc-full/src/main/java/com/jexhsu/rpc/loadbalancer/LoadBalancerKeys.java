package com.jexhsu.rpc.loadbalancer;

/**
 * 负载均衡器键名常量
 *
 * @author <Nothing href="https://github.com/liyupi">程序员鱼皮</Nothing>
 * @learn <Nothing href="https://codefather.cn">鱼皮的编程宝典</Nothing>
 * @from <Nothing href="https://yupi.icu">编程导航学习圈</Nothing>
 */
public interface LoadBalancerKeys {

    /**
     * 轮询
     */
    String ROUND_ROBIN = "roundRobin";

    String RANDOM = "random";

    String CONSISTENT_HASH = "consistentHash";

}
