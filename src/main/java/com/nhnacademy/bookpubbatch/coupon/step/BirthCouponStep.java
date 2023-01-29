package com.nhnacademy.bookpubbatch.coupon.step;


import com.nhnacademy.bookpubbatch.coupon.dto.BirthMemberDto;
import com.nhnacademy.bookpubbatch.coupon.mapper.BirthMemberDtoMapper;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.database.ItemPreparedStatementSetter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 회원의 생일을 조회하여 생일쿠폰을 지급하는 Step 입니다.
 *
 * @author : 김서현
 * @since : 1.0
 **/
@Slf4j
@Configuration
@RequiredArgsConstructor
public class BirthCouponStep {

    private final StepBuilderFactory stepBuilderFactory;

    /**
     * 회원 번호, 생일을 pageSize 단위로 Read.
     *
     * @return 회원 정보 조회.
     * @throws Exception dataSource 에서 read 과정 실패시 에러.
     */
    @Bean
    public JdbcPagingItemReader<BirthMemberDto> birthMemberDtoItemReader(
            @Qualifier("shopDb") DataSource dataSource)
            throws Exception {

        return new JdbcPagingItemReaderBuilder<BirthMemberDto>()
                .name("birthDayDtoItemReader")
                .dataSource(dataSource)
                .queryProvider(pagingQueryProvider(dataSource))
                .pageSize(1000)
                .rowMapper(new BirthMemberDtoMapper())
                .build();
    }

    /**
     * +7일 생일자에게 쿠폰 발급.
     *
     * @param dataSource dataSource.
     * @return ItemWriter.
     */
    public JdbcBatchItemWriter<BirthMemberDto> birthMemberDtoItemWriter(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<BirthMemberDto>()
                .dataSource(dataSource)
                .sql("insert into coupon(coupon_template_number, member_number, coupon_used)  "
                        + "values (1, ?, false)")
                .itemPreparedStatementSetter(new BirthdayMemberDtoPreparedStatementSetter())
                .build();
    }

    /**
     * ItemReader, ItemWriter 실행하기 위한 Step.
     *
     * @param dataSource dataSource
     * @return Step
     * @throws Exception Step 실행 시 에러
     */
    @Bean
    public Step sendBirthCouponStep(@Qualifier("shopDb") DataSource dataSource) throws Exception {
        return stepBuilderFactory.get("sendBirthCouponStep")
                .<BirthMemberDto, BirthMemberDto>chunk(1000)
                .reader(birthMemberDtoItemReader(dataSource))
                .writer(birthMemberDtoItemWriter(dataSource))
                .build();

    }

    /**
     * 현재 시각으로 부터 +7일인 생일자 조회 쿼리.
     *
     * @return 사용하려는 데이터베이스 전용 PagingQueryProvider 구현체를 구성.
     * @throws Exception 데이터베이스 감지를 못했을 때 에러.
     */
    private PagingQueryProvider pagingQueryProvider(DataSource dataSource)
            throws Exception {
        SqlPagingQueryProviderFactoryBean factoryBean = new SqlPagingQueryProviderFactoryBean();

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime sevenDaysAfter = now.plusDays(7);
        String birthDay = sevenDaysAfter.format(DateTimeFormatter.ofPattern("MMdd"));

        factoryBean.setSelectClause("select member_number, member_birth_month");
        factoryBean.setFromClause("from member");
        factoryBean.setWhereClause("where member_birth_month = " + birthDay
                + " and member_deleted= 0 and member_blocked = 0");
        factoryBean.setSortKey("member_number");
        factoryBean.setDataSource(dataSource);

        return factoryBean.getObject();

    }

    /**
     * PreparedStatement 에 매개변수 값을 설정합니다. ItemWriter 쿼리의 변수 값을 ItemReader 에서 조회한 멤버 번호로 설정해줍니다.
     */
    static class BirthdayMemberDtoPreparedStatementSetter implements
            ItemPreparedStatementSetter<BirthMemberDto> {

        @Override
        public void setValues(BirthMemberDto item, PreparedStatement ps) throws SQLException {
            ps.setLong(1, item.getMemberNo());
        }

    }

}
