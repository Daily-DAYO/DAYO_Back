package com.seoultech.dayo.heart.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HeartMemberDto {

  private String memberId;
  private String nickname;
  private boolean isFollow;

}
