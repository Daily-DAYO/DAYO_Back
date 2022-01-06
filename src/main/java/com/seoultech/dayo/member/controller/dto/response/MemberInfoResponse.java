package com.seoultech.dayo.member.controller.dto.response;

import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberInfoResponse {

    private String memberId;

    private String email;

    private String nickname;

    private String profileImg;

    public static MemberInfoResponse from(Member member) {
        return new MemberInfoResponse(member.getId(), member.getEmail(), member.getNickname(), member.getProfileImg());
    }

}
