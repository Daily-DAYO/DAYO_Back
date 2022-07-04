package com.seoultech.dayo.comment.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.seoultech.dayo.alarm.Topic;
import com.seoultech.dayo.alarm.service.AlarmService;
import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.comment.controller.dto.request.CreateCommentRequest;
import com.seoultech.dayo.comment.repository.CommentRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.service.PostService;
import com.seoultech.dayo.utils.KafkaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class CommentServiceTests {

  @Mock
  CommentRepository commentRepository;

  @Mock
  PostService postService;

  @Mock
  AlarmService alarmService;

  @Mock
  KafkaProducer kafkaProducer;

  @InjectMocks
  CommentService commentService;

  Member member;
  Post post;
  Comment comment;

  @BeforeEach
  void init() {
    member = new Member("조재영", "jdyj@naver.com");
    member.setNickname("재영");
    post = new Post(1L, member, "테스트1", "testimage", Category.SCHEDULER, null);
    comment = new Comment(1L, member, "테스트 댓글1");
  }

  @Test
  @DisplayName("댓글 생성")
  void commentTest1() {
    CreateCommentRequest request = new CreateCommentRequest("테스트 댓글1", 1L);

    given(commentRepository.save(any())).willReturn(comment);
    given(postService.findPostById(any())).willReturn(post);

    commentService.createComment(member, request);

    assertThat(post.getComments().size()).isEqualTo(1);
    assertThat(post.getComments().get(0).getId()).isEqualTo(1L);
    assertThat(comment.getPost().getId()).isEqualTo(1);

  }

  @Test
  @DisplayName("댓글 생성 - 알람 테스트 내 post 일 때")
  void commentTest2() {
    CreateCommentRequest request = new CreateCommentRequest("테스트 댓글1", post.getId());

    member.setDeviceToken("test");
    given(commentRepository.save(any())).willReturn(comment);
    given(postService.findPostById(any())).willReturn(post);
    doNothing().when(kafkaProducer).sendMessage(Topic.COMMENT, "테스트 메세지");

    commentService.createComment(member, request);

    verify(kafkaProducer, times(0)).sendMessage(Topic.COMMENT,
        "{\"subject\":\"DAYO\",\"topic\":\"COMMENT\",\"postId\":\"2\",\"body\":\"재영님이 회원님의 게시글에 댓글을 남겼어요.\",\"content\":\"님이 회원님의 게시글에 댓글을 남겼어요.\",\"deviceToken\":\"test\"}");
  }

  @Test
  @DisplayName("댓글 생성 - 알람 테스트 내 post 가 아닐 때")
  void commentTest3() {
    Member otherMember = new Member("홍길동", "other@naver.com");
    otherMember.setDeviceToken("test");
    Post otherPost = new Post(2L, otherMember, "테스트1", "testimage", Category.SCHEDULER, null);
    CreateCommentRequest request = new CreateCommentRequest("테스트 댓글1", otherPost.getId());

    given(commentRepository.save(any())).willReturn(comment);
    given(postService.findPostById(any())).willReturn(otherPost);
    doNothing().when(kafkaProducer).sendMessage(Topic.COMMENT, "테스트 메세지");

    commentService.createComment(member, request);

    verify(kafkaProducer, times(1)).sendMessage(Topic.COMMENT,
        "{\"subject\":\"DAYO\",\"topic\":\"COMMENT\",\"postId\":\"2\",\"body\":\"재영님이 회원님의 게시글에 댓글을 남겼어요.\",\"content\":\"님이 회원님의 게시글에 댓글을 남겼어요.\",\"deviceToken\":\"test\"}"
    );
  }


}