package com.seoultech.dayo.config.fcm;

import com.seoultech.dayo.alarm.Alarm;
import com.seoultech.dayo.alarm.Topic;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import java.util.HashMap;
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

  public static Note makeNote(Map<String, String> collect) {

    Map<String, String> tempData = new HashMap<>(collect);
    return new Note(
        "DAYO",
        tempData.get("content"),
        tempData,
        tempData.get("image")
    );
  }

  public Alarm toEntityWithPostId(Member member, Post post, Member sender, Topic category) {
    return new Alarm(
        member,
        category,
        false,
        this.subject,
        this.content,
        this.image,
        post,
        sender
    );
  }

  public Alarm toEntityWithoutPostId(Member member, Member sender, Topic category) {
    return new Alarm(
        member,
        category,
        false,
        this.subject,
        this.content,
        this.image,
        sender
    );
  }

}