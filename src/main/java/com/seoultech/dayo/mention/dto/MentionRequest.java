package com.seoultech.dayo.mention.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MentionRequest {

  private String memberId;
  private String nickname;
  private Integer order;

}