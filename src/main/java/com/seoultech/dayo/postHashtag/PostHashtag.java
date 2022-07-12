package com.seoultech.dayo.postHashtag;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.post.Post;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@ToString
public class PostHashtag {

  @EmbeddedId
  private Key key;

  @ManyToOne(fetch = FetchType.EAGER)
  @MapsId("hashtagId")
  private Hashtag hashtag;

  @ManyToOne(fetch = FetchType.EAGER)
  @MapsId("postId")
  private Post post;

  public PostHashtag(Post post, Hashtag hashtag) {
    this.post = post;
    this.hashtag = hashtag;
    post.getPostHashtags().add(this);
    hashtag.getPostHashtags().add(this);
    key = new Key(hashtag.getId(), post.getId());
  }

  @Embeddable
  @AllArgsConstructor
  @Getter
  @EqualsAndHashCode
  public static class Key implements Serializable {

    private Long hashtagId;
    private Long postId;

    protected Key() {
    }
  }

  protected PostHashtag() {
  }

}
