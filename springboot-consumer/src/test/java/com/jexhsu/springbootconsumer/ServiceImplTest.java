package com.jexhsu.springbootconsumer;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class ServiceImplTest {

    @Resource
    private ServiceImpl serviceImpl;

    @Test
    void test1() {
        serviceImpl.test();
    }
}
