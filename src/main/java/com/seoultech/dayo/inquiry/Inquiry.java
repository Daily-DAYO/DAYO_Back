package com.seoultech.dayo.inquiry;

import com.seoultech.dayo.member.Member;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Inquiry {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Member member;

  private String content;

  public Inquiry(Member member, String content) {
    this.member = member;
    this.content = content;
  }

  protected Inquiry() {

  }
}
