package com.seoultech.dayo.report.controller;

import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.report.controller.dto.request.CreateReportCommentRequest;
import com.seoultech.dayo.report.controller.dto.request.CreateReportMemberRequest;
import com.seoultech.dayo.report.service.ReportService;
import com.seoultech.dayo.report.service.ReportServiceV2;
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
@RequestMapping("/api/v2/reports")
public class ReportControllerV2 {

    private final ReportServiceV2 reportService;
    private final MemberService memberService;

    @PostMapping("/comment")
    public ResponseEntity<Void> saveCommentReport(@ApiIgnore @LoginUser String memberId, @RequestBody
    CreateReportCommentRequest request) {
        Member member = memberService.findMemberById(memberId);

        reportService.saveCommentReport(member, request);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
