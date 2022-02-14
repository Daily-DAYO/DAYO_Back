package com.seoultech.dayo.bookmark.controller.dto.response;

import com.seoultech.dayo.bookmark.Bookmark;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class CreateBookmarkResponse {

  private String memberId;

  private Long postId;

  public static CreateBookmarkResponse from(Bookmark bookmark) {
    return new CreateBookmarkResponse(bookmark.getKey().getMemberId(),
        bookmark.getKey().getPostId());
  }
}
