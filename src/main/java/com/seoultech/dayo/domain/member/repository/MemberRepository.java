package com.seoultech.dayo.domain.member.repository;

import com.seoultech.dayo.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findMemberByEmail(String email);

    @Query("select m from Member m join fetch m.folders where m.id = :id")
    Optional<Member> findMemberByIdWithJoin(String id);
}
