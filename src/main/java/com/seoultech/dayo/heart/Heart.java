package com.seoultech.dayo.heart;


import com.seoultech.dayo.BaseTimeEntity;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
public class Heart extends BaseTimeEntity {


  @EmbeddedId
  private Key key;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("memberId")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("postId")
  private Post post;

  public Heart(Member member, Post post) {
    this.member = member;
    this.post = post;
    key = new Key(member.getId(), post.getId());
    post.getHearts().add(this);
  }

  @Embeddable
  @AllArgsConstructor
  @Getter
  @EqualsAndHashCode
  public static class Key implements Serializable {

    private String memberId;
    private Long postId;

    protected Key() {
    }
  }

  protected Heart() {
  }
}
