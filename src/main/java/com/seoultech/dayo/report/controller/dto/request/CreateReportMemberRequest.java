package com.seoultech.dayo.report.controller.dto.request;

import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.report.Category;
import com.seoultech.dayo.report.Report;
import lombok.Getter;

@Getter
public class CreateReportMemberRequest {

  private String comment;

  private String memberId;

  public Report toEntity(Member member, Member reportedMember) {
    return new Report(this.comment, member, reportedMember, Category.POST);
  }


}
