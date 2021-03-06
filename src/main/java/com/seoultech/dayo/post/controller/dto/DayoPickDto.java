package com.seoultech.dayo.post.controller.dto;

import com.seoultech.dayo.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DayoPickDto {

  private Long id;

  private String thumbnailImage;

  private String memberId;

  private String nickname;

  private String userProfileImage;

  private Integer heartCount;

  private Integer commentCount;

  public static DayoPickDto from(Post post) {
    return new DayoPickDto(post.getId(),
        post.getThumbnailImage() + "_220x220",
        post.getMember().getId(),
        post.getMember().getNickname(),
        post.getMember().getProfileImg().getStoreFileName() + "_17x17",
        post.getHeartCount(),
        post.getCommentCount()
    );
  }

}
