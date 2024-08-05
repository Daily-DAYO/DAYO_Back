package com.seoultech.dayo.comment.repository;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.member.Member;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

  void deleteAllByMember(Member member);

  @Query("select c from Comment c where c.parent = null and c.post.id = :postId")
  List<Comment> findCommentsByPostIdWithoutReply(@Param("postId") Long postId);

}
