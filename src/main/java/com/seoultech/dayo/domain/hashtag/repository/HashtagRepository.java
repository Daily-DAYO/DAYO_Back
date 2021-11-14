package com.seoultech.dayo.domain.hashtag.repository;

import com.seoultech.dayo.domain.hashtag.Hashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {
}
