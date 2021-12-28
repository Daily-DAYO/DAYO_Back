package com.seoultech.dayo.exception;

public class NotExistFolderException extends RuntimeException {

    private static final String MESSAGE = "존재하지 않는 폴더입니다.";

    public NotExistFolderException() {
        super(MESSAGE);
    }

}
