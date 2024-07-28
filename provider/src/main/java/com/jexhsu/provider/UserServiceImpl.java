package com.jexhsu.provider;

import com.jexhsu.common.model.User;
import com.jexhsu.common.service.UserService;

/**
 * 用户服务实现类
 */
public class UserServiceImpl implements UserService {

    public String thumbs_up(User user) {
        String str = " o(￣▽￣)ｄ you are the best. \uD83D\uDC4D \uD83D\uDC4D \uD83D\uDC4D";
        return  user.getName() + str;
    }
}
