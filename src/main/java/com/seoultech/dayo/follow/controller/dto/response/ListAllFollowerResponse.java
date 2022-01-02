package com.seoultech.dayo.follow.controller.dto.response;

import com.seoultech.dayo.follow.controller.dto.FollowerDto;
import com.seoultech.dayo.follow.controller.dto.FollowingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListAllFollowerResponse {

    private int count;

    private List<FollowerDto> data;

    public static ListAllFollowerResponse from(List<FollowerDto> collect) {
        return new ListAllFollowerResponse(collect.size(), collect);
    }

}
