package com.seoultech.dayo.report.repository;

import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

  void deleteAllByMember(Member member);
}
