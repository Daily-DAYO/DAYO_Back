package com.seoultech.dayo.alarm.controller.dto;

import com.seoultech.dayo.alarm.Alarm;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmDto {

  private Long alarmId;

  private String content;

  private boolean isCheck;

  private Long postId;

  private String nickname;

  private LocalDateTime createdTime;

  public static AlarmDto from(Alarm alarm) {
    return new AlarmDto(alarm.getId(), alarm.getContent(), alarm.isCheck(), alarm.getPostId(),
        alarm.getNickname(), alarm.getCreatedDate());
  }

}
