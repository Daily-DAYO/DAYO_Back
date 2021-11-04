package com.seoultech.dayo.dto.member;

import com.seoultech.dayo.domain.member.Member;
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
