package com.seoultech.dayo.inquiry.controller;

import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.inquiry.controller.dto.request.CreateInquiryRequest;
import com.seoultech.dayo.inquiry.service.InquiryService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/inquiry")
@RequiredArgsConstructor
public class InquiryController {

  private final InquiryService inquiryService;
  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<Void> create(@LoginUser String memberId,
      CreateInquiryRequest request) {
    Member member = memberService.findMemberById(memberId);

    inquiryService.create(request, member);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
