package com.seoultech.dayo.heart.controller.dto.response;

import com.seoultech.dayo.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteHeartResponse {

  private int allCount;

  public static DeleteHeartResponse from(Post post) {
    return new DeleteHeartResponse(post.getHeartCount());
  }
}
