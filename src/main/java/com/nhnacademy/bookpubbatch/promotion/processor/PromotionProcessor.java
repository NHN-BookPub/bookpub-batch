package com.nhnacademy.bookpubbatch.promotion.processor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.nhnacademy.bookpubbatch.promotion.exception.MemberNotFoundException;
import com.nhnacademy.bookpubbatch.repository.member.dto.MemberDto;
import com.nhnacademy.bookpubbatch.repository.member.dto.MemberPromotionDto;
import com.nhnacademy.bookpubbatch.repository.tier.dto.TierDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

/**
 * Execution 에 있는 값을 가공하는 Processor 입니다.
 *
 * @author : 유호철
 * @since : 1.0
 **/
@Slf4j
@StepScope
@Component
@RequiredArgsConstructor
public class PromotionProcessor implements ItemProcessor<MemberDto, MemberPromotionDto> {
    private List<TierDto> tiers;

    @Override
    public MemberPromotionDto process(MemberDto memberDto) {
        return tiers.stream()
                .filter(t -> t.getTierPrice() <= memberDto.getTotalPrice())
                .map(t -> new MemberPromotionDto(t, memberDto))
                .findFirst()
                .orElseThrow(MemberNotFoundException::new);
    }

    @BeforeStep
    public void retrieve(StepExecution stepExecution) {
        ObjectMapper mapper = new ObjectMapper();
        JobExecution jobExecution = stepExecution.getJobExecution();

        tiers = mapper.convertValue(jobExecution.getExecutionContext().get("tiers"), TypeFactory.defaultInstance()
                .constructCollectionType(List.class, TierDto.class));
    }

}
