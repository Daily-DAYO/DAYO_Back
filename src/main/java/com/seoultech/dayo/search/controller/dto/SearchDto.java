package com.seoultech.dayo.search.controller.dto;

import com.seoultech.dayo.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchDto {

  private Long postId;

  private String thumbnailImage;

  public static SearchDto from(Post post) {
    return new SearchDto(post.getId(), post.getThumbnailImage().getStoreFileName());
  }
}
