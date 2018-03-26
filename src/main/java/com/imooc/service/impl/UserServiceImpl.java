package com.imooc.service.impl;

import com.imooc.dataobject.User;
import com.imooc.dataobject.mapper.UserMapper;
import com.imooc.dto.UserDto;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import com.imooc.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * Created by lee on 2018/1/9.
 */
@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户登陆
     */
    public User login(UserDto userDto) {
        String username = userDto.getUsername();
        String password = userDto.getPassword();
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new SellException(ResultEnum.PARAM_ERROR, "用户名或密码错误，请重新登陆!");
        }
        User user = new User();
        BeanUtils.copyProperties(userDto, user);
        int num = userMapper.checkUsername(user);
        if (num == 0) {
            throw new SellException(ResultEnum.PARAM_ERROR, "用户不存在，请确认后重新登陆!");
        }
        User userLogin = userMapper.selectLogin(user);
        if (userLogin == null) {
            throw new SellException(ResultEnum.PARAM_ERROR, "密码错误，请确认后重新登陆!");
        }
        return user;
    }
}
