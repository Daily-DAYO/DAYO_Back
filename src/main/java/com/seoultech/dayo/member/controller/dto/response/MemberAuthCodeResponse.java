package com.seoultech.dayo.member.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberAuthCodeResponse {

  private String authCode;

  public static MemberAuthCodeResponse from(String authCode) {
    return new MemberAuthCodeResponse(authCode);
  }

}
