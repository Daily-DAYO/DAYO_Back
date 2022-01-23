package com.seoultech.dayo.follow.controller.dto.response;

import com.seoultech.dayo.follow.Follow;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CreateFollowUpResponse {

    private String memberId;

    private String followerId;

    private Boolean isAccept;

    public static CreateFollowUpResponse from(Follow follow) {
        return new CreateFollowUpResponse(follow.getKey().getMemberId(), follow.getKey().getFollowerId(), follow.getIsAccept());
    }

}
