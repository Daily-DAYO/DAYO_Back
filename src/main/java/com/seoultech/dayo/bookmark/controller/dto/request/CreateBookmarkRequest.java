package com.seoultech.dayo.bookmark.controller.dto.request;

import com.seoultech.dayo.bookmark.Bookmark;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import javax.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class CreateBookmarkRequest {

  @NotNull
  private Long postId;

  public Bookmark toEntity(Member member, Post post) {
    return new Bookmark(member, post);
  }

}
