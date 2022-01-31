package com.seoultech.dayo.exception;

public class InvalidFolderAccess extends RuntimeException {

  private static final String MESSAGE = "올바르지 않은 폴더 접근입니다.";

  public InvalidFolderAccess() {
    super(MESSAGE);
  }
}
