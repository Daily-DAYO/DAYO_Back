package com.seoultech.dayo.comment;

import com.seoultech.dayo.BaseTimeEntity;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.mention.Mention;
import com.seoultech.dayo.post.Post;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

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

  @ManyToOne
  @JoinColumn(name = "SUPER_COMMENT_ID", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private Comment parent;

  @OneToMany(
      mappedBy = "parent",
      fetch = FetchType.LAZY
  )
  @JoinColumn(foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
  private List<Comment> children = new ArrayList<>();

  public void addPost(Post post) {
    this.post = post;
    post.getComments().add(this);
  }

  public void addParent(Comment parent) {
    this.parent = parent;
    this.parent.children.add(this);
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
