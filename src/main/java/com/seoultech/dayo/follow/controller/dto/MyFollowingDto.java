package com.seoultech.dayo.follow.controller.dto;

import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyFollowingDto {

  private String memberId;

  private String nickname;

  private String profileImg;

  private Boolean isFollow;

  public static MyFollowingDto from(Follow follow) {
    return new MyFollowingDto(
        follow.getFollower().getId(),
        follow.getFollower().getNickname(),
        follow.getFollower().getProfileImg().getStoreFileName() + "_45x45",
        true
    );
  }
}
