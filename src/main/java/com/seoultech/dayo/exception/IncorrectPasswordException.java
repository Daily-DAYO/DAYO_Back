package com.seoultech.dayo.exception;

public class IncorrectPasswordException extends RuntimeException {

  private static final String MESSAGE = "비밀번호가 일치하지 않습니다";

  public IncorrectPasswordException() {
    super(MESSAGE);
  }
}
