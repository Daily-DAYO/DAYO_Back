package com.seoultech.dayo.exception;

public class NotExistAlarmException extends RuntimeException {

  private static final String MESSAGE = "존재하지 않는 알람입니다";

  public NotExistAlarmException() {
    super(MESSAGE);
  }
}
