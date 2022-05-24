package com.seoultech.dayo.config.fcm;

import com.seoultech.dayo.alarm.Alarm;
import com.seoultech.dayo.alarm.Category;
import com.seoultech.dayo.member.Member;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Note {

  private String subject;
  private String content;
  private Map<String, String> data;
  private String image;

  public Alarm toEntityWithPostId(Member member, Long postId, String nickname, Category category) {
    return new Alarm(
        member,
        category,
        false,
        this.subject,
        this.content,
        this.image,
        postId,
        nickname
    );
  }

  public Alarm toEntityWithoutPostId(Member member, String nickname, Category category) {
    return new Alarm(
        member,
        category,
        false,
        this.subject,
        this.content,
        this.image,
        nickname
    );
  }

}