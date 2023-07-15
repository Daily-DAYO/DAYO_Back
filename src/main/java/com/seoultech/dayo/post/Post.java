package com.seoultech.dayo.post;


import com.seoultech.dayo.BaseTimeEntity;
import com.seoultech.dayo.bookmark.Bookmark;
import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.folder.Privacy;
import com.seoultech.dayo.heart.Heart;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.postHashtag.PostHashtag;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

@Entity
@Getter
public class Post extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @Setter
  private String contents;

  @OneToOne
  private Image thumbnailImage;

  @Setter
  @Enumerated(EnumType.STRING)
  private Category category;

  @Setter
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

  @Basic(fetch = FetchType.LAZY)
  @Formula("(select count(1) from heart h where h.post_id = id)")
  private int heartCount;

  @Basic(fetch = FetchType.LAZY)
  @Formula("(select count(1) from comment c where c.post_id = id)")
  private int commentCount;

  public void addFolder(Folder folder) {
    this.folder = folder;
    this.privacy = folder.getPrivacy();
    folder.getPosts().add(this);
  }

  public void setFolder(Folder folder) {
    this.folder = folder;
    this.setPrivacy(folder.getPrivacy());
  }

  public void deleteHeart(Heart heart) {
    hearts.removeIf(heart1 -> heart1.getKey().equals(heart.getKey()));
  }

  public void deleteBookmark(Bookmark bookmark) {
    bookmarks.removeIf(bookmark1 -> bookmark1.getKey().equals(bookmark.getKey()));
  }

  public void deletePostHashTag() {
    postHashtags.clear();
  }

  public Post(Member member, String contents, Image thumbnailImage, Category category,
      List<Image> images) {
    this.member = member;
    member.getPosts().add(this);
    this.contents = contents;
    this.thumbnailImage = thumbnailImage;
    this.category = category;
    this.images = images;
  }

  @Builder
  public Post(Long id, Member member, String contents, Image thumbnailImage, Category category,
      List<Image> images) {
    this.id = id;
    this.member = member;
    member.getPosts().add(this);
    this.contents = contents;
    this.thumbnailImage = thumbnailImage;
    this.category = category;
    this.images = images;
  }

  protected Post() {
  }
}
