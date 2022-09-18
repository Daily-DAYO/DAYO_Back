package com.seoultech.dayo.report.controller;

import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.report.controller.dto.request.CreateReportMemberRequest;
import com.seoultech.dayo.report.controller.dto.request.CreateReportPostRequest;
import com.seoultech.dayo.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

  private final ReportService reportService;
  private final MemberService memberService;

  @PostMapping("/post")
  public ResponseEntity<Void> savePostReport(@ApiIgnore @LoginUser String memberId, @RequestBody
      CreateReportPostRequest request) {
    Member member = memberService.findMemberById(memberId);
    reportService.savePostReport(member, request);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

  @PostMapping("/member")
  public ResponseEntity<Void> saveMemberReport(@ApiIgnore @LoginUser String memberId, @RequestBody
      CreateReportMemberRequest request) {
    Member member = memberService.findMemberById(memberId);
    Member reportedMember = memberService.findMemberById(request.getMemberId());

    reportService.saveMemberReport(member, reportedMember, request);
    return new ResponseEntity<>(HttpStatus.CREATED);
  }

}
