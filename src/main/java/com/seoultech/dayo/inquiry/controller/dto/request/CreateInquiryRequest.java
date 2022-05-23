package com.seoultech.dayo.inquiry.controller.dto.request;

import com.seoultech.dayo.inquiry.Inquiry;
import com.seoultech.dayo.member.Member;
import lombok.Getter;

@Getter
public class CreateInquiryRequest {

  private String content;

  public Inquiry toEntity(Member member) {
    return new Inquiry(member, content);
  }

}
