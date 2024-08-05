package com.seoultech.dayo.report.service;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.comment.service.CommentServiceV2;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.service.PostService;
import com.seoultech.dayo.report.Report;
import com.seoultech.dayo.report.controller.dto.request.CreateReportCommentRequest;
import com.seoultech.dayo.report.controller.dto.request.CreateReportMemberRequest;
import com.seoultech.dayo.report.controller.dto.request.CreateReportPostRequest;
import com.seoultech.dayo.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportServiceV2 {

  private final ReportRepository reportRepository;
  private final CommentServiceV2 commentService;

  public void saveCommentReport(Member member, CreateReportCommentRequest request) {
    Comment comment = commentService.findById(request.getCommentId());
    Report report = request.toEntity(member, comment);
    reportRepository.save(report);
  }

}
