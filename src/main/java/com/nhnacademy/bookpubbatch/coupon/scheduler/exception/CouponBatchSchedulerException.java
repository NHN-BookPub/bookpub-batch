package com.nhnacademy.bookpubbatch.coupon.scheduler.exception;

/**
 * 쿠폰배치 스케줄러 예외처리입니다.
 *
 * @author : 김서현
 * @since : 1.0
 **/
public class CouponBatchSchedulerException extends RuntimeException {

    public CouponBatchSchedulerException(String message) {
        super(message);
    }
}
