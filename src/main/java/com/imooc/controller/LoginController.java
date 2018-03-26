package com.imooc.controller;

import com.imooc.common.Const;
import com.imooc.dataobject.User;
import com.imooc.dto.UserDto;
import com.imooc.enums.ResultEnum;
import com.imooc.service.UserService;
import com.imooc.viewobject.JsonVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisCluster;
import util.CookieUtil;
import util.JsonUtil;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by lee on 2018/1/9.
 */
@RestController
@RequestMapping(value = "/user/manage")
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private JedisCluster jedisCluster;

    @RequestMapping(value = "/login")
    public JsonVo<User> login(UserDto userDto, HttpSession session, HttpServletResponse response) {
        User user = userService.login(userDto);
        if (user != null) {
            // redis共享cookie、session的实现方式
            CookieUtil.writeLoginToken(response, session.getId());
            jedisCluster.setex(session.getId(), Const.RedisCacheExtime.REDIS_SESSION_EXPIRE, JsonUtil.obj2String(user));
            return new JsonVo<>(user);
        }
        return new JsonVo<>(ResultEnum.PARAM_ERROR.getCode(), "用户不存在，请确认后重新登陆!");
    }
}
