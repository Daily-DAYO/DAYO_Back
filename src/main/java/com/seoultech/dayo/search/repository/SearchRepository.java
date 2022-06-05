package com.seoultech.dayo.search.repository;

import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.search.Search;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SearchRepository extends JpaRepository<Search, Long> {

  List<Search> findSearchesByMember(Member member);

  void deleteAllByMember(Member member);

}
