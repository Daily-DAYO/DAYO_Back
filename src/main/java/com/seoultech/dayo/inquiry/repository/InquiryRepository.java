package com.seoultech.dayo.inquiry.repository;

import com.seoultech.dayo.inquiry.Inquiry;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<Inquiry, Long> {

}
