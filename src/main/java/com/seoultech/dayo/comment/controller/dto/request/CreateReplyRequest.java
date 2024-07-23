package com.seoultech.dayo.comment.controller.dto.request;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.mention.dto.MentionRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReplyRequest {

  @NotBlank
  private String contents;

  @NotNull
  private Long postId;

  @NotBlank
  private Long commentId;

  private List<MentionRequest> mentionList = new ArrayList<>();

  public Comment toEntity(Member member, Comment upComment) {
    return new Comment(member, this.contents);
  }


}
