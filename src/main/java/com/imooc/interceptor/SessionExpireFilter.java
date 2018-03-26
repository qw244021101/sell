package com.imooc.interceptor;

import com.imooc.common.Const;
import com.imooc.dataobject.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisCluster;
import util.CookieUtil;
import util.JsonUtil;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by lee on 2018/1/10.
 */
@Slf4j
public class SessionExpireFilter implements Filter {

    @Autowired
    private JedisCluster jedisCluster;

    public void init(FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 此方法用于判断Session是否过期，如果Session存在，则延长有效时间
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        String token = CookieUtil.readLoginToken(request);
        if (StringUtils.isNotEmpty(token)) {
            // 判断用户信息是否存在于Redis
            String userStr = jedisCluster.get(token);
            User user = JsonUtil.string2Obj(userStr, User.class);
            if (user != null) {
                // 重置Session时间
                jedisCluster.expire(token, Const.RedisCacheExtime.REDIS_SESSION_EXPIRE);
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    public void destroy() {

    }
}
