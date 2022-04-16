package com.seoultech.dayo.config.fcm;

import com.seoultech.dayo.alarm.Alarm;
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

  public Alarm toEntity(Member member) {
    return new Alarm(
        member,
        false,
        this.subject,
        this.content,
        this.image
    );
  }

}