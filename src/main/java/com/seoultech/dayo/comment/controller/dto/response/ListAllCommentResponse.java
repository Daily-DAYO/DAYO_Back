package com.seoultech.dayo.comment.controller.dto.response;

import com.seoultech.dayo.comment.Comment;
import java.time.format.DateTimeFormatter;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

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
    private String createTime;

    public static CommentDto from(Comment comment) {
      return new CommentDto(comment.getId(), comment.getMember().getId(),
          comment.getMember().getNickname(), comment.getMember().getProfileImg().getStoreFileName(),
          comment.getContents(), comment.getCreatedDate().format(
          DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")));
    }

  }
}
