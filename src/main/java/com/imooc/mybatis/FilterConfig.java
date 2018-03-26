package com.imooc.mybatis;

import com.imooc.interceptor.SessionExpireFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义过滤器
 * Created by lee on 2018/1/10.
 */
@Configuration
public class FilterConfig {

    @Bean
    public SessionExpireFilter sessionExpireFilter() {
        return new SessionExpireFilter();
    }

    @Bean
    public FilterRegistrationBean filterRegistration() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(sessionExpireFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}
