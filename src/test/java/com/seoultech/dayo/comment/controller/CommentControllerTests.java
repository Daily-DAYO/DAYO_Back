package com.seoultech.dayo.comment.controller;

import static com.seoultech.dayo.ApiDocumentUtils.getDocumentRequest;
import static com.seoultech.dayo.ApiDocumentUtils.getDocumentResponse;
import static com.seoultech.dayo.image.Category.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.graph.ElementOrder;
import com.seoultech.dayo.comment.Comment;
import com.seoultech.dayo.comment.controller.dto.request.CreateCommentRequest;
import com.seoultech.dayo.comment.controller.dto.response.CreateCommentResponse;
import com.seoultech.dayo.comment.controller.dto.response.ListAllCommentResponse;
import com.seoultech.dayo.comment.controller.dto.response.ListAllCommentResponse.CommentDto;
import com.seoultech.dayo.comment.service.CommentService;
import com.seoultech.dayo.config.jwt.TokenDto;
import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.folder.Privacy;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

@WebMvcTest({CommentController.class})
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
class CommentControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CommentService commentService;

  @MockBean
  private MemberService memberService;

  TokenProvider tokenProvider = new TokenProvider();
  Member member;
  Post post;
  TokenDto token;

  @BeforeEach
  void init() {
    Image image = new Image("test.jpg", "test.jpg", POST);
    member = new Member("?????????", "jdyj@naver.com", image);
    member.setNickname("??????????????????");
    post = Post.builder()
        .id(1L)
        .member(member)
        .contents("????????? ?????????")
        .thumbnailImage("test.jpg")
        .category(Category.SCHEDULER)
        .build();

    token = tokenProvider.generateToken(member.getId());
  }

  @Test
  @DisplayName("?????? ??????")
  void createCommentControllerTest() throws Exception {

    //given
    CreateCommentRequest request = new CreateCommentRequest("?????? ?????????", post.getId());
    Comment comment = new Comment(1L, member, "?????? ?????????");

    //when
    CreateCommentResponse response = new CreateCommentResponse(comment.getId());
    given(commentService.createComment(any(), any())).willReturn(response);
    given(memberService.findMemberById(any())).willReturn(member);

    MockHttpServletRequestBuilder mockBuilder = post("/api/v1/comments").contentType(
            MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8))
        .requestAttr("memberId", member.getId())
        .servletPath("/api/v1/comments");

    ResultActions result = this.mockMvc.perform(mockBuilder);

    result.andExpect(status().isCreated())
        .andDo(print())
        .andDo(
            document("create-comment",
                getDocumentRequest(),
                getDocumentResponse(),
                requestFields(
                    fieldWithPath("contents").type(JsonFieldType.STRING).description("?????? ??????"),
                    fieldWithPath("postId").type(JsonFieldType.NUMBER).description("?????? ????????? ????????? id")
                ),
                responseFields(
                    fieldWithPath("commentId").type(JsonFieldType.NUMBER).description("????????? ?????? id")
                )
            )
        );

  }

  @Test
  @DisplayName("???????????? ???????????? ?????? ?????? ??????")
  void listAllCommentControllerTest() throws Exception {

    //given
    Comment comment1 = new Comment(1L, member, "?????? ????????? 1");
    Comment comment2 = new Comment(2L, member, "?????? ????????? 2");
    comment1.addPost(post);
    comment2.addPost(post);

    List<CommentDto> collect = new ArrayList<>();
    ListAllCommentResponse.CommentDto dto1 = ListAllCommentResponse.CommentDto.from(comment1);
    ListAllCommentResponse.CommentDto dto2 = ListAllCommentResponse.CommentDto.from(comment2);
    collect.add(dto1);
    collect.add(dto2);

    //when
    ListAllCommentResponse response = ListAllCommentResponse.from(collect);
    given(commentService.listAllComment(any())).willReturn(response);

    MockHttpServletRequestBuilder mockBuilder = get("/api/v1/comments/{postId}",
        post.getId()).contentType(
            MediaType.APPLICATION_JSON)
        .requestAttr("memberId", member.getId())
        .servletPath("/api/v1/comments/" + post.getId());

    ResultActions result = this.mockMvc.perform(mockBuilder);

    result.andExpect(status().isOk())
        .andDo(print())
        .andDo(
            document("list-comment",
                getDocumentRequest(),
                getDocumentResponse(),
                pathParameters(
                    parameterWithName("postId").description("????????? id")
                ),
                responseFields(
                    fieldWithPath("count").type(JsonFieldType.NUMBER).description("?????? ??????"),
                    fieldWithPath("data[].commentId").type(JsonFieldType.NUMBER)
                        .description("?????? id"),
                    fieldWithPath("data[].memberId").type(JsonFieldType.STRING)
                        .description("?????? ????????? ?????? id"),
                    fieldWithPath("data[].nickname").type(JsonFieldType.STRING)
                        .description("?????? ????????? ?????? ?????????"),
                    fieldWithPath("data[].profileImg").type(JsonFieldType.STRING)
                        .description("?????? ????????? ?????? ???????????????"),
                    fieldWithPath("data[].contents").type(JsonFieldType.STRING)
                        .description("?????? ??????"),
                    fieldWithPath("data[].createTime").type(JsonFieldType.STRING)
                        .description("?????? ????????????").optional()
                ))
        );


  }

}