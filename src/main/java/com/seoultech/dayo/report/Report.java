package com.seoultech.dayo.report;

import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Report {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String comment;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member reportedMember;

  @Enumerated(EnumType.STRING)
  private Category category;

  public Report(String comment, Member member, Post post, Category category) {
    this.comment = comment;
    this.member = member;
    this.post = post;
    this.category = category;
  }

  public Report(String comment, Member member, Member reportedMember, Category category) {
    this.comment = comment;
    this.member = member;
    this.reportedMember = reportedMember;
    this.category = category;
  }

  protected Report() {

  }
}
