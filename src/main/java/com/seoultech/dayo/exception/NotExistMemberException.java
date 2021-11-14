package com.seoultech.dayo.exception;

public class NotExistMemberException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 회원입니다.";

    public NotExistMemberException() {
        super(MESSAGE);
    }

    public static String getErrorMessage() {
        return MESSAGE;
    }

}
