package com.seoultech.dayo.member.controller.dto.response;

import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberMyProfileResponse {

  private String nickname;

  private String profileImg;

  private String email;

  private int followingCount;

  private int followerCount;

  private int postCount;

  public static MemberMyProfileResponse from(Member member) {
    return new MemberMyProfileResponse(
        member.getNickname(),
        member.getProfileImg().getStoreFileName(),
        member.getEmail(),
        member.getFollowingCount(),
        member.getFollowerCount(),
        member.getPostCount()
    );
  }

}
