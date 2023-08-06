package com.seoultech.dayo.heart.controller.dto.response;

import com.seoultech.dayo.heart.controller.dto.HeartMemberDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 게시글의 좋아요한 멤버 리스트
 */
@Getter
@AllArgsConstructor
public class PostMemberHeartListResponse {

  private int count;

  private boolean last;

  private List<HeartMemberDto> data;

  public static PostMemberHeartListResponse from(List<HeartMemberDto> collect, boolean last) {
    return new PostMemberHeartListResponse(collect.size(), last, collect);
  }

}
