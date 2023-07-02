package com.seoultech.dayo.notice.service;

import com.seoultech.dayo.notice.Notice;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class NoticeDto {

  private Long id;

  private String title;

  private String createdDate;

  public static NoticeDto from(Notice notice) {
    return new NoticeDto(notice.getId(), notice.getTitle(), notice.getCreatedDate()
        .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")));
  }

}
