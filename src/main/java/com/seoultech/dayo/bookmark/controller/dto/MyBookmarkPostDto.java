package com.seoultech.dayo.bookmark.controller.dto;

import com.seoultech.dayo.bookmark.Bookmark;
import com.seoultech.dayo.heart.controller.dto.MyHeartPostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyBookmarkPostDto {

  private Long postId;

  private String thumbnailImage;

  public static MyBookmarkPostDto from(Bookmark bookmark) {
    return new MyBookmarkPostDto(bookmark.getPost().getId(),
        bookmark.getPost().getThumbnailImage().getStoreFileName());
  }
}
