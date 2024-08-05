package com.seoultech.dayo.report.controller.dto.request;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.report.Category;
import com.seoultech.dayo.report.Report;
import lombok.Getter;

@Getter
public class CreateReportCommentRequest {

  private String comment;

  private Long commentId;

  public Report toEntity(Member member, Comment entityComment) {
    return new Report(this.comment, member, entityComment, Category.MEMBER);
  }


}
