package com.seoultech.dayo.comment.service;

import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.comment.controller.request.CreateCommentRequest;
import com.seoultech.dayo.comment.controller.response.CreateCommentResponse;
import com.seoultech.dayo.comment.controller.response.ListAllCommentResponse;
import com.seoultech.dayo.comment.repository.CommentRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.Privacy;
import com.seoultech.dayo.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.LinkedList;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;


@ExtendWith(MockitoExtension.class)
class CommentServiceTests {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private CommentService commentService;

    private Member member;
    private Post post;

    @BeforeEach
    void init() {
        member = new Member("테스트", "jdyj@naver.com", "test.jpg");

        post = Post.builder()
                .id(1L)
                .member(member)
                .contents("테스트 첫개시")
                .thumbnailImage("test.jpg")
                .category(Category.SCHEDULER)
                .privacy(Privacy.ALL)
                .build();

    }

    @Test
    @DisplayName("댓글 생성")
    void createCommentTest() {

        //given
        given(postRepository.findById(any())).willReturn(Optional.of(post));
        given(memberRepository.findById(any())).willReturn(Optional.of(member));

        //when
        CreateCommentRequest request = new CreateCommentRequest(member.getId(), "댓글 테스트1", post.getId());
        Comment comment = new Comment(1L, member, "댓글 테스트1");
        given(commentRepository.save(any())).willReturn(comment);
        CreateCommentResponse response = commentService.createComment(request);

        //then
        assertThat(response.getCommentId()).isEqualTo(comment.getId());
        assertThat(post.getComments().get(0).getId()).isEqualTo(comment.getPost().getId());

    }

    @Test
    @DisplayName("게시글에 존재하는 전체 댓글 조회")
    void listAllCommentTest() {

        //given
        Comment comment = new Comment(1L, member, "댓글 테스트1");
        comment.addPost(post);
        given(postRepository.findById(any())).willReturn(Optional.of(post));

        //when
        ListAllCommentResponse response = commentService.listAllComment(post.getId());

        //then
        assertThat(response.getCount()).isEqualTo(1);
        assertThat(response.getData().get(0).getCommentId()).isEqualTo(comment.getId());
        assertThat(response.getData().get(0).getProfileImg()).isEqualTo(member.getProfileImg());

    }

}