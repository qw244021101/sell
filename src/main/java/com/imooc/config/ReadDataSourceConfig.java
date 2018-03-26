package com.imooc.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.github.pagehelper.PageHelper;
import com.imooc.mybatis.DruidConfigProperties;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

@Configuration
@MapperScan(basePackages = "com.imooc.dataobject.mapper", sqlSessionFactoryRef = "readSqlSessionFactory")
public class ReadDataSourceConfig {

    @Autowired
    private DruidConfigProperties druidConfigProperties;

    private static final String MAPPER_PATH = "classpath*:/mapper/*.xml";

    private static final String ENTITY_PACKAGE = "com.imooc.dataobject";

    @Bean
    public PageHelper pageHelper(){
        System.out.println("MyBatis Configuration pageHelper");
        PageHelper pageHelper = new PageHelper();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum","true");
        p.setProperty("rowBoundsWithCount","true");
        p.setProperty("reasonable","true");
        pageHelper.setProperties(p);
        return pageHelper;
    }

    @Bean(name = "readDataSource")
    public DataSource readDataSource() throws SQLException {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(druidConfigProperties.getDriverClassName());
        druidDataSource.setUrl(druidConfigProperties.getUrl());
        druidDataSource.setUsername(druidConfigProperties.getUsername());
        druidDataSource.setPassword(druidConfigProperties.getPassword());
        druidDataSource.setMaxActive(druidConfigProperties.getMaxActive());
        druidDataSource.setInitialSize(druidConfigProperties.getInitialSize());
        druidDataSource.init();
        return druidDataSource;
    }

    @Bean(name = "readTransactionManager")
    public DataSourceTransactionManager writeTransactionManager() throws SQLException {
        return new DataSourceTransactionManager(readDataSource());
    }

    @Bean(name = "readSqlSessionFactory")
    public SqlSessionFactory writeSqlSessionFactory(@Qualifier("readDataSource")DataSource dataSource) throws Exception {
        SqlSessionFactoryBean sessionFactoryBean = new SqlSessionFactoryBean();
        sessionFactoryBean.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        sessionFactoryBean.setMapperLocations(resolver.getResources(MAPPER_PATH));
        sessionFactoryBean.setTypeAliasesPackage(ENTITY_PACKAGE);

        PageHelper pageHelper = new PageHelper();
        Properties prop = new Properties();
        prop.setProperty("dialect", "mysql");
        prop.setProperty("reasonable", "false");
        prop.setProperty("pageSizeZero", "true");
        prop.setProperty("supportMethodsArguments", "true");
        prop.setProperty("returnPageInfo", "check");
        prop.setProperty("params", "count=countSql");
        pageHelper.setProperties(prop);

        sessionFactoryBean.setPlugins(new Interceptor[]{pageHelper});
        return sessionFactoryBean.getObject();
    }
}
