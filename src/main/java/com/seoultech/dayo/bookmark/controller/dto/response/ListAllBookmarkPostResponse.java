package com.seoultech.dayo.bookmark.controller.dto.response;

import com.seoultech.dayo.bookmark.controller.dto.BookmarkPostDto;
import com.seoultech.dayo.heart.controller.dto.HeartPostDto;
import com.seoultech.dayo.heart.controller.dto.response.ListAllHeartPostResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ListAllBookmarkPostResponse {

  private int count;

  private List<BookmarkPostDto> data;

  public static ListAllBookmarkPostResponse from(List<BookmarkPostDto> collect) {
    return new ListAllBookmarkPostResponse(collect.size(), collect);
  }

}
