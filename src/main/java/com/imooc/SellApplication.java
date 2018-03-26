package com.imooc;

import com.imooc.common.SpringBootContext;
import com.imooc.service.impl.ApplicationStartup;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
@MapperScan(basePackages = "com.imooc.dataobject.mapper")
//@EnableAutoConfiguration(exclude = {DataSourceAutoConfiguration.class})
public class SellApplication {

	public static void main(String[] args) {
		SpringApplication springApplication = new SpringApplication(SellApplication.class);
		springApplication.addListeners(new ApplicationStartup());
		ApplicationContext applicationContext = springApplication.run(args);
		SpringBootContext.setApplicationContext(applicationContext);
	}
}
