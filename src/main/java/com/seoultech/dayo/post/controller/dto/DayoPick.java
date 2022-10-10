package com.seoultech.dayo.post.controller.dto;

import com.seoultech.dayo.post.Post;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DayoPick implements Serializable {

  private Long id;

  private String thumbnailImage;

  private String memberId;

  private String nickname;

  private String userProfileImage;

  private Integer heartCount;

  private Integer commentCount;

  private boolean isHeart;

  public static DayoPick from(Post post, boolean isHeart) {
    return new DayoPick(post.getId(),
        post.getThumbnailImage().getResizeFileName(220, 220),
        post.getMember().getId(),
        post.getMember().getNickname(),
        post.getMember().getProfileImg().getResizeFileName(17, 17),
        post.getHeartCount(),
        post.getCommentCount(),
        isHeart
    );
  }

  public static DayoPick fromDto(DayoPickDto dayoPickDto, boolean isHeart) {
    return new DayoPick(
        dayoPickDto.getId(),
        dayoPickDto.getThumbnailImage(),
        dayoPickDto.getMemberId(),
        dayoPickDto.getNickname(),
        dayoPickDto.getUserProfileImage(),
        dayoPickDto.getHeartCount(),
        dayoPickDto.getCommentCount(),
        isHeart
    );
  }


}
