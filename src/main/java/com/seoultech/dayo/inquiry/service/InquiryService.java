package com.seoultech.dayo.inquiry.service;

import com.seoultech.dayo.inquiry.controller.dto.request.CreateInquiryRequest;
import com.seoultech.dayo.inquiry.repository.InquiryRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InquiryService {

  private final InquiryRepository inquiryRepository;
  private final MemberService memberService;

  public void create(CreateInquiryRequest request, String memberId) {
    Member member = memberService.findMemberById(memberId);
    inquiryRepository.save(request.toEntity(member));
  }


}
