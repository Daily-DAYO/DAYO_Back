package com.seoultech.dayo.comment;

import com.seoultech.dayo.BaseTimeEntity;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.mention.Mention;
import com.seoultech.dayo.post.Post;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import lombok.Getter;

@Entity
@Getter
public class Comment extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Post post;

  @ManyToOne(fetch = FetchType.EAGER)
  private Member member;

  @OneToMany(
      mappedBy = "comment",
      orphanRemoval = true
  )
  private List<Mention> mentions = new ArrayList<>();

  private String contents;

  public void addPost(Post post) {
    this.post = post;
    post.getComments().add(this);
  }

  public void delete() {
    post.getComments().removeIf(comment -> comment.getId().equals(this.id));
  }

  public Comment(Member member, String contents) {
    this.member = member;
    this.contents = contents;
  }

  public Comment(Long id, Member member, String contents) {
    this.id = id;
    this.member = member;
    this.contents = contents;
  }

  protected Comment() {
  }

}
