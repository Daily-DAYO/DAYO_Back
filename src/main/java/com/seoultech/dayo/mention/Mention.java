package com.seoultech.dayo.mention;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.member.Member;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.Getter;

@Entity
@Getter
public class Mention {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Comment comment;

  @OneToOne(fetch = FetchType.LAZY)
  private Member member;

  private String nickname;

  private Integer ordering;

  public void addMention(Comment comment) {
    this.comment = comment;
    comment.getMentions().add(this);
  }

  public Mention(Comment comment, Member member, String nickname, Integer ordering) {
    this.comment = comment;
    this.member = member;
    this.nickname = nickname;
    this.ordering = ordering;
  }

  public Mention() {
  }
}
