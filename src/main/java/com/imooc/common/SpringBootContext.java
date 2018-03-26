package com.imooc.common;

import org.springframework.context.ApplicationContext;

public class SpringBootContext {

    private static ApplicationContext applicationContext;

    public static void setApplicationContext(ApplicationContext context) {
        applicationContext = context;
    }

    public static Object getBean(String beanId) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(beanId);
    }

    public static <T> T getBean(String beanId, Class<T> clazz) {
        if (applicationContext == null) {
            return null;
        }
        return applicationContext.getBean(beanId, clazz);
    }
}
