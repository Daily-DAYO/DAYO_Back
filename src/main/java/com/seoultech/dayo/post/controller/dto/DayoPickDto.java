package com.seoultech.dayo.post.controller.dto;

import com.seoultech.dayo.post.Post;
import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DayoPickDto implements Serializable {

  private Long id;

  private String thumbnailImage;

  private String memberId;

  private String nickname;

  private String userProfileImage;

  private Integer heartCount;

  private Integer commentCount;

  private boolean isHeart;

  public static DayoPickDto from(Post post, boolean isHeart) {
    return new DayoPickDto(post.getId(),
        post.getThumbnailImage().getResizeFileName(220, 220),
        post.getMember().getId(),
        post.getMember().getNickname(),
        post.getMember().getProfileImg().getResizeFileName(17, 17),
        post.getHeartCount(),
        post.getCommentCount(),
        isHeart
    );
  }

  public static DayoPickDto from(DayoPick dayoPick, boolean isHeart) {
    return new DayoPickDto(dayoPick.getId(),
        dayoPick.getThumbnailImage(),
        dayoPick.getMemberId(),
        dayoPick.getNickname(),
        dayoPick.getUserProfileImage(),
        dayoPick.getHeartCount(),
        dayoPick.getCommentCount(),
        isHeart
    );
  }

}
