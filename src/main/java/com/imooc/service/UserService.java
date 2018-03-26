package com.imooc.service;


import com.imooc.dataobject.User;
import com.imooc.dto.UserDto;

import java.util.List;

/**
 * Created by lee on 2018/1/9.
 */
public interface UserService {
    /** 用户登陆 */
    User login(UserDto userDto);
}
