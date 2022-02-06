package com.seoultech.dayo.member.controller.dto.response;


import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class MemberSignUpResponse {

  private String memberId;

  public static MemberSignUpResponse from(Member member) {
    return new MemberSignUpResponse(member.getId());
  }

}
