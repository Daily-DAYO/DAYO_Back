package com.seoultech.dayo.search.controller.dto;

import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchMemberDto {

  private String memberId;

  private String thumbnailImage;

  public static SearchMemberDto from(Member member) {
    return new SearchMemberDto(member.getId(), member.getProfileImg().getResizeFileName(17, 17));
  }

}
