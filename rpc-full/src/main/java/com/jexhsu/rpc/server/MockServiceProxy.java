package com.jexhsu.rpc.server;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Mock service proxy using JDK dynamic proxies.
 */
@Slf4j
public class MockServiceProxy implements InvocationHandler {

    private final Faker faker = new Faker();

    /**
     * Handles method calls on the proxy.
     *
     * @return Default value based on the method's return type.
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> returnType = method.getReturnType();
        log.info("Mock invoke {}", method.getName());
        return generateDefaultValue(returnType);
    }

    /**
     * Generates default values based on the type.
     *
     * @param type Return type of the method.
     * @return Default value for the given type.
     */
    private Object generateDefaultValue(Class<?> type) {
        // Primitive types
        if (type.isPrimitive()) {
            if (type == boolean.class) return false;
            if (type == short.class) return (short) 0;
            if (type == int.class) return 0;
            if (type == long.class) return 0L;
            if (type == float.class) return 0.0f;
            if (type == double.class) return 0.0;
        }
        // Wrapper types
        if (type == Boolean.class) return false;
        if (type == Short.class) return (short) 0;
        if (type == Integer.class) return 0;
        if (type == Long.class) return 0L;
        if (type == Float.class) return 0.0f;
        if (type == Double.class) return 0.0;

        // Object types
        if (type == String.class) return faker.lorem().word();
        if (type == java.util.Date.class) return faker.date().birthday();
        // Add more types as needed
        return null;
    }
}
