package com.seoultech.dayo.exception;

public class NotExistCommentException extends RuntimeException {

  private final static String MESSAGE = "존재하지 않는 댓글입니다.";

  public NotExistCommentException() {
    super(MESSAGE);
  }
}
