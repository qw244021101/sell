//package com.imooc.mybatis;
//
//import com.alibaba.druid.pool.DruidDataSource;
//import com.github.pagehelper.PageHelper;
//import org.apache.ibatis.plugin.Interceptor;
//import org.apache.ibatis.session.SqlSessionFactory;
//import org.mybatis.spring.SqlSessionFactoryBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
//import org.springframework.jdbc.datasource.DataSourceTransactionManager;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//import java.sql.SQLException;
//import java.util.HashMap;
//import java.util.Properties;
//import java.util.Map;
//
//@Configuration
//public class MyBatisConfig {
//
//    @Autowired
//    private DruidConfigProperties druidConfigProperties;
//
//    @Bean
//    public PageHelper pageHelper(){
//        System.out.println("MyBatis Configuration pageHelper");
//        PageHelper pageHelper = new PageHelper();
//        Properties p = new Properties();
//        p.setProperty("offsetAsPageNum","true");
//        p.setProperty("rowBoundsWithCount","true");
//        p.setProperty("reasonable","true");
//        pageHelper.setProperties(p);
//        return pageHelper;
//    }
//
//    @Bean
//    @Primary
//    public DataSource writeDataSource() throws SQLException {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(druidConfigProperties.getDriverClassName());
//        druidDataSource.setUrl(druidConfigProperties.getUrl());
//        druidDataSource.setUsername(druidConfigProperties.getUsername());
//        druidDataSource.setPassword(druidConfigProperties.getPassword());
//        druidDataSource.setMaxActive(druidConfigProperties.getMaxActive());
//        druidDataSource.setInitialSize(druidConfigProperties.getInitialSize());
//        druidDataSource.init();
//        return druidDataSource;
//    }
//
//    @Bean
//    public DataSource readDataSource() throws SQLException {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(druidConfigProperties.getDriverClassName());
//        druidDataSource.setUrl(druidConfigProperties.getUrl());
//        druidDataSource.setUsername(druidConfigProperties.getUsername());
//        druidDataSource.setPassword(druidConfigProperties.getPassword());
//        druidDataSource.setMaxActive(druidConfigProperties.getMaxActive());
//        druidDataSource.setInitialSize(druidConfigProperties.getInitialSize());
//        druidDataSource.init();
//        return druidDataSource;
//    }
//
//    @Bean
//    public DataSource dynamicDataSource() throws SQLException {
//        DynamicDataSource dynamicDataSource = new DynamicDataSource();
//        Map<Object, Object> targetDataSource = new HashMap<>();
//        targetDataSource.put("readDataSource", readDataSource());
//        targetDataSource.put("writeDataSource", writeDataSource());
//        dynamicDataSource.setTargetDataSources(targetDataSource);
//        dynamicDataSource.setDefaultTargetDataSource(writeDataSource());
//        return dynamicDataSource;
//    }
//
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource")DataSource dataSource) throws Exception {
//        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
//        sessionFactoryBean.setDataSource(dataSource);
//        sessionFactoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:/mapper/*.xml"));
//
//        PageHelper pageHelper = new PageHelper();
//        Properties prop = new Properties();
//        prop.setProperty("dialect", "mysql");
//        prop.setProperty("reasonable", "false");
//        prop.setProperty("pageSizeZero", "true");
//        prop.setProperty("supportMethodsArguments", "true");
//        prop.setProperty("returnPageInfo", "check");
//        prop.setProperty("params", "count=countSql");
//        pageHelper.setProperties(prop);
//
//        sessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
//
//        return sessionFactoryBean.getObject();
//    }
//
//    @Bean
//    public PlatformTransactionManager transactionManager(@Qualifier("dynamicDataSource")DataSource dataSource) {
//        return new DataSourceTransactionManager(dataSource);
//    }
//}
