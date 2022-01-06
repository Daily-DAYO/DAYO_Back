package com.seoultech.dayo.follow.controller.dto.response;

import com.seoultech.dayo.follow.controller.dto.MyFollowingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListAllMyFollowingResponse {

    private int count;

    private List<MyFollowingDto> data;

    public static ListAllMyFollowingResponse from(List<MyFollowingDto> collect) {
        return new ListAllMyFollowingResponse(collect.size(), collect);
    }

}
