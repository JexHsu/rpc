package com.jexhsu.provider;


import com.jexhsu.common.service.UserService;
import com.jexhsu.rpc.bootstrap.ProviderBootStrap;
import com.jexhsu.rpc.model.ServiceRegisterInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 简易服务提供者示例
 */
public class Provider {

    public static void main(String[] args) {
        // 要注册的服务
        List<ServiceRegisterInfo<?>> serviceRegisterInfoList = new ArrayList<>();
        String serviceName = UserService.class.getName();
        Class<UserServiceImpl> implClass = UserServiceImpl.class;
        ServiceRegisterInfo serviceRegisterInfo = new ServiceRegisterInfo(serviceName, implClass);
        serviceRegisterInfoList.add(serviceRegisterInfo);
        // 服务提供者初始化
        ProviderBootStrap.init(serviceRegisterInfoList);
    }
}
