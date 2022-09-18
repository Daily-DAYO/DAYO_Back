package com.seoultech.dayo.exception;

public class ExistNicknameException extends RuntimeException {

  private final static String MESSAGE = "이미 존재하는 닉네임입니다.";

  public ExistNicknameException() {
    super(MESSAGE);
  }

}
