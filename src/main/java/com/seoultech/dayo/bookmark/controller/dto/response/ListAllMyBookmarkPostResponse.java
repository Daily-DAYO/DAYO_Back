package com.seoultech.dayo.bookmark.controller.dto.response;

import com.seoultech.dayo.bookmark.controller.dto.MyBookmarkPostDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListAllMyBookmarkPostResponse {

  private int count;

  private boolean last;

  private List<MyBookmarkPostDto> data;

  public static ListAllMyBookmarkPostResponse from(List<MyBookmarkPostDto> collect, boolean last) {
    return new ListAllMyBookmarkPostResponse(collect.size(), last, collect);
  }

}
