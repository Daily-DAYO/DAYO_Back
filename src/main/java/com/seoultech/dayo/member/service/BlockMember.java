package com.seoultech.dayo.member.service;


import com.seoultech.dayo.member.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BlockMember {

  private String memberId;

  private String profileImage;

  private String name;

  public static BlockMember from(Member member) {
    return new BlockMember(member.getId(), member.getProfileImg().getStoreFileName(),
        member.getNickname());
  }

}
