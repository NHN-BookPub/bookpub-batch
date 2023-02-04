package com.nhnacademy.bookpubbatch.config;

import javax.sql.DataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * 배송서버 Mybatis 설정값들이 들어있는 config 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@MapperScan(basePackages = "**.mapping.**", sqlSessionFactoryRef = "deliverySqlSessionFactory")
public class DeliveryMybatisConfig {

    @Bean("deliverySqlSessionFactory")
    public SqlSessionFactory sqlSessionFactoryBean(@Qualifier("deliveryDb") DataSource dataSource) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:**/mappings/*.xml"));
        return sessionFactory.getObject();
    }
}
