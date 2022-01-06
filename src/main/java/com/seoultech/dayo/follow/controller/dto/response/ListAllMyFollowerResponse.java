package com.seoultech.dayo.follow.controller.dto.response;

import com.seoultech.dayo.follow.controller.dto.MyFollowerDto;
import com.seoultech.dayo.follow.controller.dto.MyFollowingDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListAllMyFollowerResponse {

    private int count;

    private List<MyFollowerDto> data;

    public static ListAllMyFollowerResponse from(List<MyFollowerDto> collect) {
        return new ListAllMyFollowerResponse(collect.size(), collect);
    }

}
