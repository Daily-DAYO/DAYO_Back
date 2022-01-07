package com.seoultech.dayo.follow.controller.dto;


import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FollowingDto {

    private String memberId;

    private String nickname;

    private String profileImg;

    private Boolean isAccept;

    public static FollowingDto from(Follow follow) {
        return new FollowingDto(follow.getFollower().getId(), follow.getFollower().getNickname(), follow.getFollower().getProfileImg().getStoreFileName(), follow.getIsAccept());
    }

}
