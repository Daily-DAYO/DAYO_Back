package com.seoultech.dayo.exception;

public class NotExistFolderPrivacyException extends RuntimeException {

  private final static String MESSAGE = "존재하지 않는 공개설정 입니다.";

  public NotExistFolderPrivacyException() {
    super(MESSAGE);
  }
}
