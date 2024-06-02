package com.seoultech.dayo.search.controller.dto;

import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchMemberInCommentDto {

  private String memberId;

  private String profileImg;

  private String nickname;

  public static SearchMemberInCommentDto from(Member member) {
    return new SearchMemberInCommentDto(member.getId(),
        member.getProfileImg().getResizeFileName(17, 17),
        member.getNickname());
  }

}
