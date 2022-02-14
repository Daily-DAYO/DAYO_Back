package com.seoultech.dayo.post;


import com.seoultech.dayo.BaseTimeEntity;
import com.seoultech.dayo.bookmark.Bookmark;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.heart.Heart;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.postHashtag.PostHashtag;
import lombok.Builder;
import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.Formula;

@Entity
@Getter
public class Post extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.EAGER)
  private Member member;

  private String contents;

  private String thumbnailImage;

  @Enumerated(EnumType.STRING)
  private Category category;

  @Enumerated(EnumType.STRING)
  private Privacy privacy;

  @ManyToOne(fetch = FetchType.LAZY)
  private Folder folder;

  @OneToMany
  @JoinColumn(name = "post_id")
  private List<Image> images;

  @OneToMany(
      mappedBy = "post",
      orphanRemoval = true
  )
  private List<Comment> comments = new ArrayList<>();

  @OneToMany(
      mappedBy = "post",
      orphanRemoval = true
  )
  private List<Heart> hearts = new ArrayList<>();

  @OneToMany(
      mappedBy = "post",
      orphanRemoval = true
  )
  private List<PostHashtag> postHashtags = new ArrayList<>();

  @OneToMany(
      mappedBy = "post",
      orphanRemoval = true
  )
  private List<Bookmark> bookmarks = new ArrayList<>();

  @Formula("(select count(1) from heart h where h.post_id = id)")
  private int heartCount;

  @Formula("(select count(1) from comment c where c.post_id = id)")
  private int commentCount;

  public void addFolder(Folder folder) {
    this.folder = folder;
    folder.getPosts().add(this);
  }

  public Post(Member member, String contents, String thumbnailImage, Category category,
      Privacy privacy, List<Image> images) {
    this.member = member;
    member.getPosts().add(this);
    this.contents = contents;
    this.thumbnailImage = thumbnailImage;
    this.category = category;
    this.privacy = privacy;
    this.images = images;
  }

  @Builder
  public Post(Long id, Member member, String contents, String thumbnailImage, Category category,
      Privacy privacy, List<Image> images) {
    this.id = id;
    this.member = member;
    member.getPosts().add(this);
    this.contents = contents;
    this.thumbnailImage = thumbnailImage;
    this.category = category;
    this.privacy = privacy;
    this.images = images;
  }

  protected Post() {
  }
}
