package com.jexhsu.springbootprovider;

import com.jexhsu.common.model.User;
import com.jexhsu.common.service.UserService;
import com.jexhsu.rpc.springboot.starter.annotation.RpcService;
import org.springframework.stereotype.Service;

/**
 * 用户服务实现类
 */
@Service
@RpcService
public class UserServiceImpl implements UserService {

    @Override
    public String thumbs_up(User user) {
        String str = " o(￣▽￣)ｄ you are the best one. \uD83D\uDC4D \uD83D\uDC4D \uD83D\uDC4D";
        return user.getName() + str;
    }
}
