package com.seoultech.dayo.report.controller.dto.request;

import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.report.Report;
import lombok.Getter;

@Getter
public class CreateReportRequest {

  private String comment;

  private Long postId;

  public Report toEntity(Member member, Post post) {
    return new Report(this.comment, member, post);
  }

}
