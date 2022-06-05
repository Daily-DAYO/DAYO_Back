package com.seoultech.dayo.bookmark;

import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import java.io.Serializable;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Entity
@Getter
public class Bookmark {

  @EmbeddedId
  private Key key;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("memberId")
  private Member member;

  @ManyToOne(fetch = FetchType.LAZY)
  @MapsId("postId")
  private Post post;

  public Bookmark(Member member, Post post) {
    this.member = member;
    this.post = post;
    key = new Bookmark.Key(member.getId(), post.getId());
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

  protected Bookmark() {
  }

}
