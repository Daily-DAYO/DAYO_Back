package com.seoultech.dayo.report.controller;

import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.report.controller.dto.request.CreateReportRequest;
import com.seoultech.dayo.report.service.ReportService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ReportController {

  private final ReportService reportService;
  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<Void> saveReport(@LoginUser String memberId, @RequestBody
      CreateReportRequest request) {
    Member member = memberService.findMemberById(memberId);
    reportService.save(member, request);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
