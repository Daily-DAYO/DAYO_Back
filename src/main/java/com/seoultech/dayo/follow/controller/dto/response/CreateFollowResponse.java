package com.seoultech.dayo.follow.controller.dto.response;

import com.seoultech.dayo.follow.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateFollowResponse {

    private String memberId;

    private String followerId;

    public static CreateFollowResponse from(Follow follow) {
        return new CreateFollowResponse(follow.getMember().getId(), follow.getFollower().getId());
    }

}
