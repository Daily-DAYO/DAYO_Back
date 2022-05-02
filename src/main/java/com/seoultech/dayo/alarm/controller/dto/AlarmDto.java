package com.seoultech.dayo.alarm.controller.dto;

import com.seoultech.dayo.alarm.Alarm;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmDto {

  private String content;

  private boolean isCheck;

  private Long postId;

  private String nickname;

  public static AlarmDto from(Alarm alarm) {
    return new AlarmDto(alarm.getContent(), alarm.isCheck(), alarm.getPostId(),
        alarm.getNickname());
  }

}
