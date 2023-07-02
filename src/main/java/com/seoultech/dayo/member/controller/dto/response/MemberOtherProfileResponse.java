package com.seoultech.dayo.member.controller.dto.response;

import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class MemberOtherProfileResponse {

  private String memberId;

  private String email;

  private String nickname;

  private String profileImg;

  private int followingCount;

  private int followerCount;

  private int postCount;

  private boolean isFollow;

  public static MemberOtherProfileResponse from(Member member, boolean isFollow, int size) {
    return MemberOtherProfileResponse.builder()
        .memberId(member.getId())
        .email(member.getEmail())
        .nickname(member.getNickname())
        .profileImg(member.getProfileImg().getStoreFileName())
        .followingCount(member.getFollowingCount())
        .followerCount(member.getFollowerCount())
        .postCount(size)
        .isFollow(isFollow)
        .build();
  }

}
