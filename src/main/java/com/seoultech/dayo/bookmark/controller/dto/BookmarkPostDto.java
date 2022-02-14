package com.seoultech.dayo.bookmark.controller.dto;

import com.seoultech.dayo.bookmark.Bookmark;
import com.seoultech.dayo.heart.Heart;
import com.seoultech.dayo.heart.controller.dto.HeartPostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BookmarkPostDto {

  private Long postId;

  private String thumbnailImage;

  public static BookmarkPostDto from(Bookmark bookmark) {
    return new BookmarkPostDto(bookmark.getPost().getId(), bookmark.getPost().getThumbnailImage());
  }

}
