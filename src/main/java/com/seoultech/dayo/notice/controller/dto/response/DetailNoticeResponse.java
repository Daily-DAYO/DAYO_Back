package com.seoultech.dayo.notice.controller.dto.response;

import com.seoultech.dayo.notice.Notice;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DetailNoticeResponse {

  private String contents;

  public static DetailNoticeResponse from(Notice notice) {
    return new DetailNoticeResponse(notice.getContents());
  }

}
