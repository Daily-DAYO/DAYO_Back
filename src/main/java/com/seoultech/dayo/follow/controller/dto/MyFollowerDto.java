package com.seoultech.dayo.follow.controller.dto;

import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyFollowerDto {

    private String memberId;

    private String nickname;

    private String profileImg;

    private Boolean isAccept;

    public static MyFollowerDto from(Follow follow) {
        return new MyFollowerDto(follow.getMember().getId(), follow.getMember().getNickname(),follow.getMember().getProfileImg().getStoreFileName(), follow.getIsAccept());
    }

}
