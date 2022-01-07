package com.seoultech.dayo.heart.controller.dto.response;


import com.seoultech.dayo.heart.controller.dto.HeartPostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListAllHeartPostResponse {

    private int count;

    private List<HeartPostDto> data;

    public static ListAllHeartPostResponse from(List<HeartPostDto> collect) {
        return new ListAllHeartPostResponse(collect.size(), collect);
    }
}
