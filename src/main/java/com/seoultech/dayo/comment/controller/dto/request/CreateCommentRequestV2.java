package com.seoultech.dayo.comment.controller.dto.request;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.mention.dto.MentionRequest;
import java.util.ArrayList;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequestV2 {

  @NotBlank
  private String contents;

  @NotNull
  private Long postId;

  private List<MentionRequest> mentionList = new ArrayList<>();

  public Comment toEntity(Member member) {
    return new Comment(member, this.contents);
  }


}
