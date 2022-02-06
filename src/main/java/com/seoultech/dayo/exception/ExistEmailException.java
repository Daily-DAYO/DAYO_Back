package com.seoultech.dayo.exception;

public class ExistEmailException extends RuntimeException {

  private final static String MESSAGE = "이미 존재하는 이메일입니다.";

  public ExistEmailException() {
    super(MESSAGE);
  }
}
