package com.jexhsu.rpc.springboot.starter.annotation;

import com.jexhsu.rpc.constant.RpcConstant;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 服务提供者注解（用于注册服务）
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Component
public @interface RpcService {

    /**
     * 服务接口类
     */
    Class<?> interfaceClass() default void.class;

    /**
     * 版本
     */
    String serviceVersion() default RpcConstant.DEFAULT_SERVICE_VERSION;
}
