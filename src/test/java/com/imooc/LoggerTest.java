package com.imooc;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by win10 on 2017/10/25.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    String girl = "杨丹杰";
    String boy  = "李蕴航";
    @Test
    public void test(){
        log.debug("logger Debug");
        log.info("logger Info boy:{},girl:{}", boy, girl);
        log.error("logger Error");
    }
}
