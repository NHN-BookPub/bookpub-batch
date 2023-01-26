package com.nhnacademy.bookpubbatch.birthcoupon.job;

import com.nhnacademy.bookpubbatch.birthcoupon.dto.BirthMemberDto;
import com.nhnacademy.bookpubbatch.birthcoupon.mapper.BirthMemberDtoMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 회원의 생일을 조회해서 생일쿠폰을 지급하는 Step 입니다.
 *
 * @author : 김서현
 * @since : 1.0
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BirthCouponStep {

    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;


    /*
     ItemReader : 멤버 테이블 조회하여 오늘을 기준으로 +7일 생일인 회원 가져오기
          LocalDateTime now = LocalDateTime.now();
    		LocalDateTime sevenDaysAfter = now.plusDays(7);
    		String birthDay = sevenDaysAfter.format(DateTimeFormatter.ofPattern("MMdd"));

     select 문
          select member_number from member where member_birth_month=birthday; => set 으로 담아서


     */

    /**
     * 회원 번호, 생일을 pageSize 단위로 Read.
     *
     * @return 회원 정보 조회.
     * @throws Exception 예외.
     */

    @Bean
    public JdbcPagingItemReader<BirthMemberDto> birthMemberDtoItemReader()
            throws Exception {

        return new JdbcPagingItemReaderBuilder<BirthMemberDto>()
                .name("birthDayDtoItemReader")
                .dataSource(dataSource)
                .queryProvider(pagingQueryProvider())
                .pageSize(10)
                .rowMapper(new BirthMemberDtoMapper())
                .build();
    }

    /**
     * 현재 시각으로 부터 +7일인 생일자 조회 쿼리.
     *
     * @return 사용하려는 데이터베이스 전용 PagingQueryProvider 구현체를 구성.
     * @throws Exception 데이터베이스 감지를 못했을 때 예외.
     */

    public PagingQueryProvider pagingQueryProvider()
            throws Exception {
        SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysAfter = now.plusDays(7);
        String birthDay = sevenDaysAfter.format(DateTimeFormatter.ofPattern("MMdd"));

        factoryBean.setSelectClause("select member_number, member_birth_month");
        factoryBean.setFromClause("from member");
        factoryBean.setWhereClause("where member_birth_month = " + "0122"); //TODO : 변수 변경 birthDay
        factoryBean.setSortKey("member_number");
        factoryBean.setDataSource(dataSource);

        return factoryBean.getObject();

    }

    /*
    ItemWriter : 쿠폰 테이블에 생일쿠폰템플릿 번호 넣어서 insert 문 쿠폰 발급

     => set 에 담긴 아이디 값들 넣어서 반복문 돌리기. iter..? foreach? :memberNO;

        insert into Coupon (null, template 번호(고정), 회원번호(member_no), false(사용여부));

     */
    public JdbcBatchItemWriter<BirthMemberDto> writer() {
        return new JdbcBatchItemWriterBuilder<BirthMemberDto>()
                .dataSource(dataSource)
                .sql("insert into coupon  values (null, 60, :memberNo, false")
                .beanMapped()
                .build();
    }

    @Bean
    @JobScope
    public Step sendBirthCouponStep() throws Exception {
        return stepBuilderFactory.get("sendBirthCouponStep")
                .<BirthMemberDto, BirthMemberDto>chunk(10)
                .reader(birthMemberDtoItemReader())
                .writer(writer())
                .allowStartIfComplete(true)
                .build();

    }

}
