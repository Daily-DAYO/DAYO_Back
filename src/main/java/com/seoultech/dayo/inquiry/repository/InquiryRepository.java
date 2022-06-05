package com.seoultech.dayo.inquiry.repository;

import com.seoultech.dayo.inquiry.Inquiry;
import com.seoultech.dayo.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

  void deleteAllByMember(Member member);
}
