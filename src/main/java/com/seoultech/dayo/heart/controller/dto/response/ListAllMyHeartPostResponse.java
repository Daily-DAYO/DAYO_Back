package com.seoultech.dayo.heart.controller.dto.response;

import com.seoultech.dayo.heart.controller.dto.MyHeartPostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListAllMyHeartPostResponse {


  private int count;

  private boolean last;

  private List<MyHeartPostDto> data;

  public static ListAllMyHeartPostResponse from(List<MyHeartPostDto> collect, boolean last) {
    return new ListAllMyHeartPostResponse(collect.size(), last, collect);
  }

}
