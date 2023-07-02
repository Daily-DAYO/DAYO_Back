package com.seoultech.dayo.notice.controller.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class NoticeListResponse {

  private int count;
  private List<?> data;
  private boolean last;

  public static NoticeListResponse from(List<?> collect, boolean last) {
    return new NoticeListResponse(collect.size(), collect, last);
  }

}
