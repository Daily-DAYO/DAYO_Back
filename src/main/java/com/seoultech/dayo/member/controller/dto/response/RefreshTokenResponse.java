package com.seoultech.dayo.member.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RefreshTokenResponse {

  private String accessToken;

  public static RefreshTokenResponse from(String accessToken) {
    return new RefreshTokenResponse(accessToken);
  }

}
