package com.seoultech.dayo.exception;

public class NotExistPostException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 게시글입니다";

    public NotExistPostException() {
        super(MESSAGE);
    }

}
