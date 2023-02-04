package com.nhnacademy.bookpubbatch.config;


import com.nhnacademy.bookpubbatch.repository.coupon.dto.CouponDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryResponseDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryShippingResponseDto;
import com.nhnacademy.bookpubbatch.repository.delivery.dto.DeliveryStateDto;
import com.nhnacademy.bookpubbatch.repository.member.dto.MemberDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderDto;
import com.nhnacademy.bookpubbatch.repository.order.dto.OrderStateDto;
import com.nhnacademy.bookpubbatch.repository.tier.dto.TierDto;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.batch.core.repository.ExecutionContextSerializer;
import org.springframework.batch.core.repository.dao.Jackson2ExecutionContextStringSerializer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import javax.sql.DataSource;
import java.io.IOException;


/**
 * 마이바티스의 설정값들이 들어있는 config 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Configuration
@MapperScan(basePackages = "**.mapper.**", sqlSessionFactoryRef = "sqlSessionFactory")
public class MybatisConfig {

    /**
     * 마이바티스에서 SessionFactory 를 생성해주기위한 Bean 입니다.
     *
     * @param dataSource shop datasource 기입
     * @return the sql session factory bean
     * @throws IOException the io exception
     */
    @Primary
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("shopDb") DataSource dataSource) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSource);
        sessionFactory.setMapperLocations(resolver.getResources("classpath*:**/maps/*.xml"));
        return sessionFactory.getObject();
    }

    /**
     * Mybatis 에서 Spring batch 값변환시 serializer error 를 잡아주기위한 Bean 입니다.
     *
     * @return the execution context serializer
     */
    @Bean
    public ExecutionContextSerializer customSerializer() {
        return new Jackson2ExecutionContextStringSerializer(MemberDto.class.getName(),
                TierDto.class.getName(),
                OrderDto.class.getName(),
                OrderStateDto.class.getName(),
                CouponDto.class.getName(),
                DeliveryStateDto.class.getName(),
                DeliveryResponseDto.class.getName(),
                DeliveryShippingResponseDto.class.getName());
    }

}
