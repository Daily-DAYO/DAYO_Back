package com.seoultech.dayo.member.repository;

import com.seoultech.dayo.member.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

  Optional<Member> findMemberByEmail(String email);

  boolean existsMemberByEmail(String email);

  Optional<Member> findMemberByEmailAndPassword(String email, String password);

  @Query("select m from Member m left join m.folders where m.id = :id")
  Optional<Member> findMemberByIdUsingJoinFolder(@Param("id") String id);

  boolean existsMemberByNickname(String nickname);

  @Query("select m from Member m join fetch m.blockList where m.id = :id")
  Optional<Member> findMemberByIdUsingJoinBlock(@Param("id") String id);

}
