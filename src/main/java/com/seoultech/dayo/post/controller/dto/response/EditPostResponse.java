package com.seoultech.dayo.post.controller.dto.response;


import com.seoultech.dayo.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EditPostResponse {

  private Long postId;

  public static EditPostResponse from(Post post) {
    return new EditPostResponse(post.getId());
  }

}
