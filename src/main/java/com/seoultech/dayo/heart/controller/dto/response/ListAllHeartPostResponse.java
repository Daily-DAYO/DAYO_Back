package com.seoultech.dayo.heart.controller.dto.response;


import com.seoultech.dayo.heart.controller.dto.HeartPostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListAllHeartPostResponse {

    private int count;

    private boolean last;

    private List<HeartPostDto> data;

    public static ListAllHeartPostResponse from(List<HeartPostDto> collect, boolean last) {
        return new ListAllHeartPostResponse(collect.size(), last, collect);
    }
}
