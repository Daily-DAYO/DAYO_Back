package com.seoultech.dayo.post.controller.dto.response;


import com.seoultech.dayo.post.controller.dto.FeedDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ListFeedResponse {

  private int count;

  private List<FeedDto> data;

  public static ListFeedResponse from(List<FeedDto> data) {
    return new ListFeedResponse(data.size(), data);
  }


}
