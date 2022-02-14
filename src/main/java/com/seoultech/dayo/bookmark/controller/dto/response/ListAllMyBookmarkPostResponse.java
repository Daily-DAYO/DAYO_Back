package com.seoultech.dayo.bookmark.controller.dto.response;

import com.seoultech.dayo.bookmark.controller.dto.MyBookmarkPostDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListAllMyBookmarkPostResponse {

  private int count;

  private List<MyBookmarkPostDto> data;

  public static ListAllMyBookmarkPostResponse from(List<MyBookmarkPostDto> collect) {
    return new ListAllMyBookmarkPostResponse(collect.size(), collect);
  }

}
