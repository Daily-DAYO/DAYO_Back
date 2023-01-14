package com.seoultech.dayo.member.controller.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MemberListResponse {

  private List<?> data;
  private int count;

  public static MemberListResponse from(List<?> collect) {
    return new MemberListResponse(collect, collect.size());
  }

}
