package com.seoultech.dayo.member.controller.dto.response;

import com.seoultech.dayo.config.jwt.TokenDto;
import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberOAuthResponse {

    private String accessToken;
    private String refreshToken;

    public static MemberOAuthResponse from(TokenDto token) {
        return new MemberOAuthResponse(token.getAccessToken(), token.getRefreshToken());
    }

}
