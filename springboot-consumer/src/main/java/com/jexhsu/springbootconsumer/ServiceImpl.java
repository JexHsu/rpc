package com.jexhsu.springbootconsumer;

import com.jexhsu.common.model.User;
import com.jexhsu.common.service.UserService;
import com.jexhsu.rpc.springboot.starter.annotation.RpcReference;
import org.springframework.stereotype.Service;

@Service
public class ServiceImpl {

    @RpcReference
    private UserService userService;

    public void test() {
        User user = new User();
        user.setName("jexhsu");
        String proud = userService.thumbs_up(user);
        System.out.println(proud);
    }

}
