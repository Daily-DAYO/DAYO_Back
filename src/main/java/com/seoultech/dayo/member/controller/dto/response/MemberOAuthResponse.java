package com.seoultech.dayo.member.controller.dto.response;

import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberOAuthResponse {

    private String id;

    public static MemberOAuthResponse from(Member member) {
        return new MemberOAuthResponse(member.getId());
    }

}
