package com.seoultech.dayo.folder;

import com.seoultech.dayo.BaseTimeEntity;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Folder extends BaseTimeEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String subheading;

  @Enumerated(EnumType.STRING)
  private Privacy privacy;

  @OneToOne
  private Image thumbnailImage;

  private Integer orderIndex;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member member;

  @OneToMany(
      mappedBy = "folder",
      cascade = CascadeType.PERSIST,
      orphanRemoval = true
  )
  private List<Post> posts = new ArrayList<>();

  @Formula("(select count(1) from post p where p.folder_id = id)")
  private int postCount;

  public void setMember(Member member) {
    this.member = member;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setSubheading(String subheading) {
    this.subheading = subheading;
  }

  public void setPrivacy(Privacy privacy) {
    this.privacy = privacy;
  }

  public void setThumbnailImage(Image thumbnailImage) {
    this.thumbnailImage = thumbnailImage;
  }

  public void setOrderIndex(Integer orderIndex) {
    this.orderIndex = orderIndex;
  }

  @Builder
  public Folder(Long id, String name, String subheading, Privacy privacy, Image thumbnailImage) {
    this.id = id;
    this.name = name;
    this.subheading = subheading;
    this.privacy = privacy;
    this.thumbnailImage = thumbnailImage;
  }

  public Folder(String name, String subheading, Privacy privacy, Image thumbnailImage) {
    this.name = name;
    this.subheading = subheading;
    this.privacy = privacy;
    this.thumbnailImage = thumbnailImage;
  }

  public Folder(String name, Privacy privacy, Image thumbnailImage) {
    this.name = name;
    this.privacy = privacy;
    this.thumbnailImage = thumbnailImage;
  }

  public Folder(String name, Privacy privacy) {
    this.name = name;
    this.privacy = privacy;
  }

  protected Folder() {
  }
}
