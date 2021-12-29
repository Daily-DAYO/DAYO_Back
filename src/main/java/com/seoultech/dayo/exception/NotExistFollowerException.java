package com.seoultech.dayo.exception;

public class NotExistFollowerException extends RuntimeException {

    private final static String MESSAGE = "존재하지 않는 상대방입니다";

    public NotExistFollowerException() {
        super(MESSAGE);
    }
}
