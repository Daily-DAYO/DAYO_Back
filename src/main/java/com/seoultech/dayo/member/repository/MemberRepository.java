package com.seoultech.dayo.member.repository;

import com.seoultech.dayo.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

  Optional<Member> findMemberByEmail(String email);

  Optional<Member> findMemberByEmailAndPassword(String email, String password);

}
