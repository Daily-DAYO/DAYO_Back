package com.seoultech.dayo.alarm;

import com.seoultech.dayo.BaseTimeEntity;
import com.seoultech.dayo.member.Member;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Alarm extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @Enumerated(EnumType.STRING)
  private Topic category;

  private Long postId;

  private Boolean isCheck;

  private String subject;

  private String content;

  private String image;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member sender;

  public void setCheck(Boolean check) {
    isCheck = check;
  }

  public Alarm(Member member, Topic category, Boolean isCheck, String subject, String content,
      String image,
      Long postId, Member sender) {
    this.member = member;
    this.category = category;
    this.isCheck = isCheck;
    this.subject = subject;
    this.content = content;
    this.image = image;
    this.postId = postId;
    this.sender = sender;
  }

  public Alarm(Member member, Topic category, Boolean isCheck, String subject,
      String content, String image, Member sender) {
    this.member = member;
    this.category = category;
    this.isCheck = isCheck;
    this.subject = subject;
    this.content = content;
    this.image = image;
    this.sender = sender;
  }

  protected Alarm() {
  }
}
