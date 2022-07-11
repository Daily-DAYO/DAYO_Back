package com.seoultech.dayo.comment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.comment.repository.CommentRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class CommentServiceTest {

  @Autowired
  CommentService commentService;

  @Autowired
  CommentRepository commentRepository;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  PostRepository postRepository;

  Member member;
  Post post;
  Comment comment;

  @BeforeEach
  void init() {
    member = new Member("조재영", "jdyj444@naver.com");
    post = new Post(member, "테스트1", "testimage", Category.SCHEDULER, null);
    comment = new Comment(member, "댓글 테스트");
    comment.addPost(post);
  }

  @Test
  @Transactional
  void deleteTest() {

    Member savedMember = memberRepository.save(member);
    Post savedPost = postRepository.save(post);
    Comment savedComment = commentRepository.save(comment);

    commentService.deleteComment(savedComment.getId());

    Post findPost = postRepository.findById(savedPost.getId()).orElseThrow();

    assertThat(savedPost.getComments().size()).isEqualTo(0);

  }

}