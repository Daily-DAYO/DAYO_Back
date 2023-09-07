package com.seoultech.dayo.heart.controller.dto.response;

import com.seoultech.dayo.heart.Heart;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateHeartResponse {

  private String memberId;

  private Long postId;

  private Long allCount;

  public static CreateHeartResponse from(Heart heart, Long allCount) {
    return new CreateHeartResponse(heart.getKey().getMemberId(), heart.getKey().getPostId(),
        allCount);
  }

}
