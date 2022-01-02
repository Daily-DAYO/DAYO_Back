package com.seoultech.dayo.follow.controller.dto.response;

import com.seoultech.dayo.follow.controller.dto.FollowingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListAllFollowingResponse {

    private int count;

    private List<FollowingDto> data;

    public static ListAllFollowingResponse from(List<FollowingDto> collect) {
        return new ListAllFollowingResponse(collect.size(), collect);
    }

}
