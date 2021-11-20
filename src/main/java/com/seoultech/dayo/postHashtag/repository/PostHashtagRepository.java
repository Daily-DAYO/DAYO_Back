package com.seoultech.dayo.postHashtag.repository;


import com.seoultech.dayo.postHashtag.PostHashtag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, PostHashtag.Key> {
}
