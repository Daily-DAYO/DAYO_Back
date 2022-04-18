package com.seoultech.dayo.search;

import com.seoultech.dayo.member.Member;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import lombok.Getter;

@Entity
@Getter
public class Search {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Member member;

  private String content;

  public Search(Member member, String content) {
    this.member = member;
    this.content = content;
  }

  protected Search() {
  }
}
