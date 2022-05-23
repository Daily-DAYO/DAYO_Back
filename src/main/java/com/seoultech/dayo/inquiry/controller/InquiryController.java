package com.seoultech.dayo.inquiry.controller;

import com.seoultech.dayo.inquiry.controller.dto.request.CreateInquiryRequest;
import com.seoultech.dayo.inquiry.service.InquiryService;
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

  @PostMapping
  public ResponseEntity<Void> create(HttpServletRequest servletRequest,
      CreateInquiryRequest request) {

    String memberId = servletRequest.getAttribute("memberId").toString();
    inquiryService.create(request, memberId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
