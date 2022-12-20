package com.seoultech.dayo.block.controller;

import lombok.Getter;

public class BlockRequest {

  @Getter
  static class CreateDto {

    private String memberId;
  }

}
