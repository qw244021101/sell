package com.imooc.mybatis;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by lee on 2017/12/29.
 */
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DruidConfigProperties {
    /** 数据库驱动 */
    private String driverClassName;
    /** 连接池启动的初始连接数 */
    private Integer initialSize;
    /** 连接池的最大值 */
    private Integer maxActive;
    /** 建立连接的最大等待时间 */
    private Integer maxWait;
    /** 连接最小空闲值 */
    private Integer minIdle;
    /** 数据库连接路径 */
    private String url;
    /** 数据库用户名 */
    private String username;
    /** 数据库密码 */
    private String password;
    /** 连接的超时时间 */
    private Long minEvictableIdleTimeMillis;
    /** 取出连接检测 */
    private Boolean testOnBorrow;
    /** 归还连接检测 */
    private Boolean testOnReturn;

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public Integer getInitialSize() {
        return initialSize;
    }

    public void setInitialSize(Integer initialSize) {
        this.initialSize = initialSize;
    }

    public Integer getMaxActive() {
        return maxActive;
    }

    public void setMaxActive(Integer maxActive) {
        this.maxActive = maxActive;
    }

    public Integer getMaxWait() {
        return maxWait;
    }

    public void setMaxWait(Integer maxWait) {
        this.maxWait = maxWait;
    }

    public Integer getMinIdle() {
        return minIdle;
    }

    public void setMinIdle(Integer minIdle) {
        this.minIdle = minIdle;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getMinEvictableIdleTimeMillis() {
        return minEvictableIdleTimeMillis;
    }

    public void setMinEvictableIdleTimeMillis(Long minEvictableIdleTimeMillis) {
        this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
    }

    public Boolean getTestOnBorrow() {
        return testOnBorrow;
    }

    public void setTestOnBorrow(Boolean testOnBorrow) {
        this.testOnBorrow = testOnBorrow;
    }

    public Boolean getTestOnReturn() {
        return testOnReturn;
    }

    public void setTestOnReturn(Boolean testOnReturn) {
        this.testOnReturn = testOnReturn;
    }
}
