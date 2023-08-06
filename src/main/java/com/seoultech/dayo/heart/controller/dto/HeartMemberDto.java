package com.seoultech.dayo.heart.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeartMemberDto {

  private String memberId;
  private boolean isFollow;

}
