package com.seoultech.dayo.exception;


public class NotExistFollowException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 팔로우입니다.";

    public NotExistFollowException() {
        super(MESSAGE);
    }
}
