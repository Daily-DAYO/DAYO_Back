package com.seoultech.dayo.exception;

public class NotExistBookmarkException extends RuntimeException {

  private final static String MESSAGE = "존재하지 않는 북마크입니다.";

  public NotExistBookmarkException() {
    super(MESSAGE);
  }
}
