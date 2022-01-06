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
    private String followerId;

    public Follow toEntity(Member member, Member follower) {
        return new Follow(new Follow.Key(member.getId(), follower.getId()), member, follower, false);
    }

    public CreateFollowRequest() {
    }
}
