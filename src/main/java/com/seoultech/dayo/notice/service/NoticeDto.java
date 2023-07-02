package com.seoultech.dayo.notice.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.seoultech.dayo.notice.Notice;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class NoticeDto {

  @JsonProperty
  private Long id;

  @JsonProperty
  private String title;

  @JsonProperty
  private String contents;

  public static NoticeDto from(Notice notice) {
    return new NoticeDto(notice.getId(), notice.getTitle(), notice.getContents());
  }

}
