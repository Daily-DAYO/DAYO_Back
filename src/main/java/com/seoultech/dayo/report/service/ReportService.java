package com.seoultech.dayo.report.service;

import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.service.PostService;
import com.seoultech.dayo.report.Report;
import com.seoultech.dayo.report.controller.dto.request.CreateReportRequest;
import com.seoultech.dayo.report.repository.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportService {

  private final ReportRepository reportRepository;
  private final PostService postService;

  public void save(Member member, CreateReportRequest request) {

    Post post = postService.findPostById(request.getPostId());

    Report report = request.toEntity(member, post);
    reportRepository.save(report);

  }

}
