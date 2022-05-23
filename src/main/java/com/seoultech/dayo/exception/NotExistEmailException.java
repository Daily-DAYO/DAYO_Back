package com.seoultech.dayo.exception;

public class NotExistEmailException extends RuntimeException {

  private static final String MESSAGE = "존재하지 않는 이메일입니다.";

  public NotExistEmailException() {
    super(MESSAGE);
  }
}
