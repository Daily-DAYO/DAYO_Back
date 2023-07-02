package com.seoultech.dayo.heart.controller.dto.response;

import com.seoultech.dayo.heart.Heart;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateHeartResponse {

  private String memberId;

  private Long postId;

  private int allCount;

  public static CreateHeartResponse from(Heart heart) {
    return new CreateHeartResponse(heart.getKey().getMemberId(), heart.getKey().getPostId(),
        heart.getPost().getHeartCount());
  }

}
