package com.seoultech.dayo.follow.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowRequest;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowUpRequest;
import com.seoultech.dayo.follow.controller.dto.response.CreateFollowResponse;
import com.seoultech.dayo.follow.controller.dto.response.CreateFollowUpResponse;
import com.seoultech.dayo.follow.service.FollowService;
import com.seoultech.dayo.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import static com.seoultech.dayo.ApiDocumentUtils.getDocumentRequest;
import static com.seoultech.dayo.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FollowController.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
class FollowControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FollowService followService;

    private Member member1;
    private Member member2;

    @BeforeEach
    void init() {
        member1 = new Member("조재영", "jdyj@naver.com", "");
        member2 = new Member("테스트", "test@naver.com", "");
        member1.setNickname("닉네임1");
        member2.setNickname("닉네임2");
    }

    @Test
    @DisplayName("팔로우 생성 컨트롤러")
    void createFollow() throws Exception {

        //given
        CreateFollowRequest request = new CreateFollowRequest(member1.getId(), member2.getId());
        Follow follow = new Follow(new Follow.Key(member1.getId(), member2.getId()), member1, member2, false);

        //when
        CreateFollowResponse response = CreateFollowResponse.from(follow);
        given(followService.createFollow(any())).willReturn(response);

        ResultActions result = this.mockMvc.perform(
                post("/api/v1/follow")
                        .content(objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        result.andExpect(status().isCreated())
                .andDo(print())
                .andDo(
                        document("create-follow",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("팔로우 하는 사람 id"),
                                        fieldWithPath("followerId").type(JsonFieldType.STRING).description("팔로우 받는 사람 id")
                                ),
                                responseFields(
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("팔로우 하는 사람 id"),
                                        fieldWithPath("followerId").type(JsonFieldType.STRING).description("팔로우 받는 사람 id"),
                                        fieldWithPath("isAccept").type(JsonFieldType.BOOLEAN).description("맞팔로우 여부")
                                )

                        )
                );

    }

    @Test
    @DisplayName("맞팔로우 생성 컨트롤러")
    void createFollowUp() throws Exception {

        //given
        CreateFollowUpRequest request = new CreateFollowUpRequest(member2.getId(), member1.getId());
        Follow follow = new Follow(new Follow.Key(member2.getId(), member1.getId()), member2, member1, true);

        //when
        CreateFollowUpResponse response = CreateFollowUpResponse.from(follow);
        given(followService.createFollowUp(any())).willReturn(response);

        ResultActions result = this.mockMvc.perform(
                post("/api/v1/follow/up")
                        .content(objectMapper.writeValueAsString(request).getBytes(StandardCharsets.UTF_8))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isCreated())
                .andDo(print())
                .andDo(
                        document("create-follow-up",
                                getDocumentRequest(),
                                getDocumentResponse(),
                                requestFields(
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("팔로우 하는 회원 id"),
                                        fieldWithPath("followerId").type(JsonFieldType.STRING).description("팔로우 받는 회원 id")
                                ),
                                responseFields(
                                        fieldWithPath("memberId").type(JsonFieldType.STRING).description("팔로우 하는 회원 id"),
                                        fieldWithPath("followerId").type(JsonFieldType.STRING).description("팔로우 받는 회원 id"),
                                        fieldWithPath("isAccept").type(JsonFieldType.BOOLEAN).description("맞팔로우 여부")
                                )
                        )
                );

    }

}