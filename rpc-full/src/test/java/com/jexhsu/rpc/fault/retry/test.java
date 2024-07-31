package com.jexhsu.rpc.fault.retry;

import com.jexhsu.rpc.model.RpcResponse;
import org.junit.Test;

/**
 * 重试策略测试
 */
public class test {
    RetryStrategy nothing = new Nothing();
    RetryStrategy fixedInterval = new FixedInterval();

    @Test
    public void doRetry() {
        try {
            RpcResponse rpcResponse = fixedInterval.doRetry(() -> {
                System.out.println("测试重试");
                throw new RuntimeException("模拟重试失败");
            });
            System.out.println(rpcResponse);
        } catch (Exception e) {
            System.out.println("重试多次失败");
            e.printStackTrace();
        }
    }
}
