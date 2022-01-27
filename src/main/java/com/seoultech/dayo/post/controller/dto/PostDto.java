package com.seoultech.dayo.post.controller.dto;


import com.seoultech.dayo.post.Post;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PostDto {

  private Long id;

  private String thumbnailImage;

  private String memberId;

  private String nickname;

  private String userProfileImage;

  private Integer heartCount;

  private Integer commentCount;

  private boolean isHeart;

  public static PostDto from(Post post, boolean isHeart) {
    return new PostDto(post.getId(),
        post.getThumbnailImage(),
        post.getMember().getId(),
        post.getMember().getNickname(),
        post.getMember().getProfileImg().getStoreFileName(),
        post.getHearts().size(),
        post.getComments().size(),
        isHeart
    );
  }

}
