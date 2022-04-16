package com.seoultech.dayo.alarm;

import com.seoultech.dayo.BaseTimeEntity;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import java.util.Map;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Alarm extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Member member;

  private Long postId;

  private boolean isCheck;

  private String subject;

  private String content;

  private String image;

  public Alarm(Member member, boolean isCheck, String subject, String content, String image,
      Long postId) {
    this.member = member;
    this.isCheck = isCheck;
    this.subject = subject;
    this.content = content;
    this.image = image;
    this.postId = postId;
  }

  protected Alarm() {
  }
}
