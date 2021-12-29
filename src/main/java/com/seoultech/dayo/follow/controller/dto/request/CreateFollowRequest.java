package com.seoultech.dayo.follow.controller.dto.request;

import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
public class CreateFollowRequest {

    @NotNull
    private String memberId;

    @NotNull
    private String followerId;

    public Follow toEntity(Member member, Member follower) {
        return new Follow(new Follow.Key(this.memberId, this.followerId), member, follower);
    }

}
