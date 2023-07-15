package com.seoultech.dayo.heart.controller.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class DeleteHeartResponse {

  private Long allCount;

  public static DeleteHeartResponse from(Long allCount) {
    return new DeleteHeartResponse(allCount);
  }
}
