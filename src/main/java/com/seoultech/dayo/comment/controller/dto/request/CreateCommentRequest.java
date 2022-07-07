package com.seoultech.dayo.comment.controller.dto.request;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateCommentRequest {

  @NotBlank
  private String contents;

  @NotNull
  private Long postId;

  public Comment toEntity(Member member) {
    return new Comment(member, this.contents);
  }

}
