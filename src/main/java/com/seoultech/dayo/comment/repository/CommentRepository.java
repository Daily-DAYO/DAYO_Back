package com.seoultech.dayo.comment.repository;

import com.seoultech.dayo.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
}
