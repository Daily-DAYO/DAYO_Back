package com.seoultech.dayo.comment.controller.dto.response;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.mention.Mention;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ListAllCommentResponseV2 {

  private int count;
  private List<CommentDto> data;

  public static ListAllCommentResponseV2 from(List<CommentDto> data) {
    return new ListAllCommentResponseV2(data.size(), data);
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
    private Long replyId;
    private List<MentionDto> mentionList;

    public static CommentDto from(Comment comment) {

      return new CommentDto(comment.getId(), comment.getMember().getId(),
          comment.getMember().getNickname(), comment.getMember().getProfileImg().getStoreFileName(),
          comment.getContents(), comment.getCreatedDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")),
          comment.getParent().getId(),
          MentionDto.from(comment.getMentions()));
    }
  }

  @Getter
  @AllArgsConstructor
  static public class MentionDto {

    private String memberId;
    private String nickname;
    private Integer ordering;

    public static MentionDto from(Mention mention) {
      return new MentionDto(mention.getMember().getId(), mention.getNickname(),
          mention.getOrdering());
    }

    public static List<MentionDto> from(List<Mention> mentionList) {

      return mentionList.stream()
          .map(mention -> new MentionDto(mention.getMember().getId(), mention.getNickname(),
              mention.getOrdering())).collect(Collectors.toList());
    }

  }
}
