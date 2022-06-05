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

  public void create(CreateInquiryRequest request, Member member) {
    inquiryRepository.save(request.toEntity(member));
  }

  public void deleteAllByMember(Member member) {
    inquiryRepository.deleteAllByMember(member);
  }


}
