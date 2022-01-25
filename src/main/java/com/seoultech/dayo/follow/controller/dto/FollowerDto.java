package com.seoultech.dayo.follow.controller.dto;

import com.seoultech.dayo.follow.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowerDto {

  private String memberId;

  private String nickname;

  private String profileImg;

  private Boolean isFollow;

  public static FollowerDto from(Follow follow, boolean isFollow) {
    return new FollowerDto(
        follow.getMember().getId(),
        follow.getMember().getNickname(),
        follow.getMember().getProfileImg().getStoreFileName(),
        isFollow
    );
  }

}
