package com.seoultech.dayo.post.controller.dto;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedDto {

  private Long id;

  private String thumbnailImage;

  private String memberId;

  private String nickname;

  private String userProfileImage;

  private Integer heartCount;

  private Integer commentCount;

  private boolean isHeart;

  private List<CommentDto> comments;

  public static FeedDto from(Post post, boolean isHeart, List<CommentDto> comments) {
    return new FeedDto(post.getId(), post.getImages().get(0).getStoreFileName(),
        post.getMember().getId(), post.getMember().getNickname(),
        post.getMember().getProfileImg().getStoreFileName(),
        post.getHeartCount(),
        post.getCommentCount(),
        isHeart,
        comments);
  }

  @Getter
  @AllArgsConstructor
  static public class CommentDto {

    private Long commentId;
    private String memberId;
    private String nickname;
    private String profileImg;
    private String contents;
    private LocalDateTime createTime;

    public static CommentDto from(Comment comment) {
      return new CommentDto(comment.getId(), comment.getMember().getId(),
          comment.getMember().getNickname(),
          comment.getMember().getProfileImg().getStoreFileName(), comment.getContents(),
          comment.getCreatedDate());
    }

  }
}
