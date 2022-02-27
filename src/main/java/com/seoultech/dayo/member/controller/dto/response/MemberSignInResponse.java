package com.seoultech.dayo.member.controller.dto.response;

import com.seoultech.dayo.config.jwt.TokenDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberSignInResponse {

  private String accessToken;
  private String refreshToken;

  public static MemberSignInResponse from(TokenDto token) {
    return new MemberSignInResponse(token.getAccessToken(), token.getRefreshToken());
  }

}
