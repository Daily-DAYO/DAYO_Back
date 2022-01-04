package com.seoultech.dayo.comment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.comment.controller.request.CreateCommentRequest;
import com.seoultech.dayo.comment.controller.response.CreateCommentResponse;
import com.seoultech.dayo.comment.controller.response.ListAllCommentResponse;
import com.seoultech.dayo.comment.service.CommentService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.Privacy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import static com.seoultech.dayo.ApiDocumentUtils.getDocumentRequest;
import static com.seoultech.dayo.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(CommentController.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
class CommentControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
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
    @DisplayName("댓글 생성 컨트롤러")
    void createCommentControllerTest() throws Exception {

        //given
        CreateCommentRequest request = new CreateCommentRequest(member.getId(), "댓글 테스트", post.getId());
        Comment comment = new Comment(1L, member, "댓글 테스트");

        //when
        CreateCommentResponse response = new CreateCommentResponse(comment.getId());
        given(commentService.createComment(any())).willReturn(response);
        ResultActions result = this.mockMvc.perform(
                post("/api/v1/comments")
                        .content(objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8))
                        .contentType(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isCreated())
                .andDo(print())
                .andDo(
                        document("create-comment",
                            getDocumentRequest(),
                            getDocumentResponse(),
                            requestFields(
                                    fieldWithPath("memberId").type(JsonFieldType.STRING).description("댓글 등록한 회원 id"),
                                    fieldWithPath("contents").type(JsonFieldType.STRING).description("댓글 내용"),
                                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("댓글 등록된 게시글 id")
                            ),
                            responseFields(
                                    fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("등록된 댓글 id")
                            )
                        )
                );

    }

    @Test
    @DisplayName("게시글에 존재하는 전체 댓글 조회 컨트롤러")
    void listAllCommentControllerTest() {

        //given
        Comment comment1 = new Comment(1L, member, "댓글 테스트 1");
        Comment comment2 = new Comment(2L, member, "댓글 테스트 2");
        comment1.addPost(post);
        comment2.addPost(post);

        List<ListAllCommentResponse.CommentDto> collect = new ArrayList<>();
        ListAllCommentResponse.CommentDto dto1 = new ListAllCommentResponse.CommentDto(comment1.getId(), member.getNickname(), member.getProfileImg(), comment1.getContents(), comment1.getCreatedDate());
        ListAllCommentResponse.CommentDto dto2 = new ListAllCommentResponse.CommentDto(comment2.getId(), member.getNickname(), member.getProfileImg(), comment2.getContents(), comment2.getCreatedDate());
        collect.add(dto1);
        collect.add(dto2);

        //when
//        new ListAllCommentResponse();


    }

}