package com.seoultech.dayo.heart.controller.dto;

import com.seoultech.dayo.heart.Heart;
import com.seoultech.dayo.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MyHeartPostDto {

  private Long postId;

  private String thumbnailImage;

  public static MyHeartPostDto from(Heart heart) {
    return new MyHeartPostDto(heart.getPost().getId(),
        heart.getPost().getThumbnailImage().getStoreFileName());
  }
}
