package com.seoultech.dayo.post.controller.dto;

import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Entity
@Getter
@AllArgsConstructor
public class DayoPick implements Serializable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String thumbnailImage;

  private String memberId;

  private String nickname;

  private String userProfileImage;

  private Integer heartCount;

  private Integer commentCount;

  private Long postId;

  @Enumerated(EnumType.STRING)
  private Category category;

  public static DayoPick from(Post post) {
    return new DayoPick(post.getId(),
        post.getThumbnailImage().getResizeFileName(220, 220),
        post.getMember().getId(),
        post.getMember().getNickname(),
        post.getMember().getProfileImg().getResizeFileName(17, 17),
        post.getHeartCount(),
        post.getCommentCount(),
        post.getId(),
        post.getCategory()
    );
  }

  protected DayoPick() {
  }
}
