package com.seoultech.dayo.domain.comment.repository;

import com.seoultech.dayo.domain.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
