package com.seoultech.dayo.comment.controller.response;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
public class ListAllCommentResponse {

  private int count;
  private List<CommentDto> data;

  public static ListAllCommentResponse from(List<CommentDto> data) {
    return new ListAllCommentResponse(data.size(), data);
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
          comment.getMember().getNickname(), comment.getMember().getProfileImg().getStoreFileName(),
          comment.getContents(), comment.getCreatedDate());
    }

  }
}
