package com.nhnacademy.bookpubbatch.promotion.exception;

/**
 * Some description here.
 *
 * @author : 유호철
 * @since : 1.0
 */
public class MemberNotFoundException extends RuntimeException {
    public static final String MESSAGE = "승급할 회원이 없습니다";
    public MemberNotFoundException() {
        super(MESSAGE);
    }
}
