package com.seoultech.dayo.domain.postHashtag.repository;


import com.seoultech.dayo.domain.postHashtag.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, PostHashtag.Key> {
}
