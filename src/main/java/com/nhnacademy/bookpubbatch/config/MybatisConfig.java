package com.nhnacademy.bookpubbatch.config;

/**
 * Some description here.
 *
 * @author : 유호철
 * @since : 1.0
 **/

import com.nhnacademy.bookpubbatch.repository.member.dto.MemberDto;
import com.nhnacademy.bookpubbatch.repository.tier.dto.TierDto;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.repository.ExecutionContextSerializer;
import org.springframework.batch.core.repository.dao.Jackson2ExecutionContextStringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;
import java.io.IOException;

@Configuration
@MapperScan(basePackages = "**.mapper.**", sqlSessionFactoryRef = "sqlSessionFactoryBean")
public class MybatisConfig {

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(@Qualifier("shopDb") DataSource dataSource) throws IOException {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:**/maps/*.xml"));
        return sessionFactory;
    }

    @Bean
    public ExecutionContextSerializer customSerializer() {
        return new Jackson2ExecutionContextStringSerializer(MemberDto.class.getName(), TierDto.class.getName());
    }

}
