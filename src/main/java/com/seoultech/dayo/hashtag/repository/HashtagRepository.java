package com.seoultech.dayo.hashtag.repository;

import com.seoultech.dayo.hashtag.Hashtag;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

  @Query("select h from Hashtag h where h.tag in :tags")
  List<Hashtag> findByTags(@Param("tags") List<String> tags);

}
