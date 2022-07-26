package com.seoultech.dayo.alarm.controller.dto;

import com.seoultech.dayo.alarm.Alarm;
import com.seoultech.dayo.post.Post;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmDto {

  private Long alarmId;

  private String content;

  private String category;

  private boolean isCheck;

  private Long postId;

  private String nickname;

  private LocalDateTime createdTime;

  private String memberId;

  private String image;

  public static AlarmDto from(Alarm alarm) {

    Post post = alarm.getPost();
    Long postId;
    if (post == null) {
      postId = null;
    } else {
      postId = post.getId();
    }

    return new AlarmDto(alarm.getId(), alarm.getContent(), alarm.getCategory().toString(),
        alarm.getIsCheck(), postId,
        alarm.getSender().getNickname(), alarm.getCreatedDate(), alarm.getSender().getId(),
        alarm.getImage());
  }

}
