package com.jexhsu.rpc.serializer;

import com.jexhsu.rpc.spi.SpiLoader;

import java.util.HashMap;
import java.util.Map;
import java.util.ServiceLoader;

/**
 * 序列化器工厂（用于获取序列化器对象）
 */
public class SerializerFactory {

//    // 硬编码 HashMap 来存储序列化器和实现类
//    /**
//     * 序列化映射（用于实现单例）
//     */
//    private static final Map<String, Serializer> KEY_SERIALIZER_MAP = new HashMap<String, Serializer>() {{
//        put(SerializerKeys.JDK, new JdkSerializer());
//        put(SerializerKeys.JSON, new JsonSerializer());
//        put(SerializerKeys.KRYO, new KryoSerializer());
//        put(SerializerKeys.HESSIAN, new HessianSerializer());
//    }};
//
//    /**
//     * 默认序列化器
//     */
//    private static final Serializer DEFAULT_SERIALIZER = KEY_SERIALIZER_MAP.get("jdk");
//
//    /**
//     * 获取实例
//     *
//     * @param key
//     * @return
//     */
//    public static Serializer getInstance(String key) {
//        return KEY_SERIALIZER_MAP.getOrDefault(key, DEFAULT_SERIALIZER);
//    }
    public static Serializer getSerializer() {
        Serializer serializer = null;
        ServiceLoader<Serializer> serviceLoader = ServiceLoader.load(Serializer.class);
        for (Serializer service : serviceLoader) {
            serializer = service;
        }
        return serializer;
    }

    // SPI 加载指定的序列化器对象
    static {
        SpiLoader.load(Serializer.class);
    }

    /**
     * 默认序列化器
     */
    private static final Serializer DEFAULT_SERIALIZER = new JdkSerializer();

    /**
     * 获取实例
     *
     * @param key
     * @return
     */
    public static Serializer getInstance(String key) {
        return SpiLoader.getInstance(Serializer.class, key);
    }
}
