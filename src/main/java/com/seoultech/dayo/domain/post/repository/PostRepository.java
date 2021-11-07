package com.seoultech.dayo.domain.post.repository;

import com.seoultech.dayo.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
