package com.imooc.interceptor;

import com.imooc.dataobject.User;
import com.imooc.enums.ResultEnum;
import com.imooc.exception.SellException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.JedisCluster;
import util.CookieUtil;
import util.JsonUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by lee on 2018/1/10.
 */
@Slf4j
public class AuthorityInterceptor implements HandlerInterceptor{

    @Autowired
    private JedisCluster jedisCluster;

    /**
     * 此方法用于拦截请求
     */
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object handler) throws Exception {
        // 获取请求路径
        HandlerMethod handlerMethod = (HandlerMethod)handler;
        // 解析handlerMethod
        String methodName = handlerMethod.getMethod().getName();
        String className  = handlerMethod.getBean().getClass().getSimpleName();
        // 解析参数
        StringBuffer requestParamBuffer = new StringBuffer();
        Map paramMap = httpServletRequest.getParameterMap();
        Iterator iterator = paramMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry entry = (Map.Entry)iterator.next();
            String key = (String) entry.getKey();
            String value = StringUtils.EMPTY;
            Object obj = entry.getValue();
            if (obj instanceof String[]) {
                String[] strs = (String[])obj;
                value = Arrays.toString(strs);
            }
            requestParamBuffer.append(key).append("=").append(value);
        }
        if (StringUtils.equals(className, "LoginController") && StringUtils.equals(methodName, "login")){
            // 登陆接口不进行拦截操作
            return true;
        }
        log.info("拦截请求, className:{}, methodName:{}, param:{}", className, methodName, requestParamBuffer.toString());
        User user = null;
        // 获取当前用户的token
        String token = CookieUtil.readLoginToken(httpServletRequest);
        if (StringUtils.isNotEmpty(token)) {
            String userStr = jedisCluster.get(token);
            user = JsonUtil.string2Obj(userStr, User.class);
        }
        if (user == null) {
            throw new SellException(ResultEnum.LOGIN_TIMEOUT, ResultEnum.LOGIN_TIMEOUT.getMessage());
        }
        return true;
    }

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
