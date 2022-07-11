package com.seoultech.dayo.exception;

public class NotExistHeartException extends RuntimeException {

  private final static String MESSAGE = "존재하지 않는 좋아요입니다.";

  public NotExistHeartException() {
    super(MESSAGE);
  }
}
