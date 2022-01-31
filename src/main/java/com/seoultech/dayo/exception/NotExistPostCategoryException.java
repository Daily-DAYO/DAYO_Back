package com.seoultech.dayo.exception;

public class NotExistPostCategoryException extends RuntimeException {

  private static final String MESSAGE = "존재하지 않는 카테고리입니다.";

  public NotExistPostCategoryException() {
    super(MESSAGE);
  }
}
