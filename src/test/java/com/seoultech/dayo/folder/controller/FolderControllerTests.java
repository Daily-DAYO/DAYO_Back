package com.seoultech.dayo.folder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.folder.Privacy;
import com.seoultech.dayo.folder.controller.dto.FolderDto;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderRequest;
import com.seoultech.dayo.folder.controller.dto.response.CreateFolderResponse;
import com.seoultech.dayo.folder.controller.dto.response.ListAllFolderResponse;
import com.seoultech.dayo.folder.service.FolderService;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;

import static com.seoultech.dayo.ApiDocumentUtils.getDocumentRequest;
import static com.seoultech.dayo.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(FolderController.class)
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@AutoConfigureRestDocs
class FolderControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FolderService folderService;

    private Member member;

    @BeforeEach
    public void init() {
        member = new Member("조재영", "jdyj@naver.com","");
    }

    @Test
    public void 폴더_생성() throws Exception {

        //given
        MockMultipartFile thumbnailImage = new MockMultipartFile("thumbnailImage","test.jpg" , "image/png" , "test.jpg".getBytes());
        Image image = new Image("테스트.jpg","test.jpg");
        CreateFolderRequest request = new CreateFolderRequest("기본 폴더", "부제목", "ALL", thumbnailImage);
        Folder folder = Folder.builder()
                .id(1L)
                .name("기본 폴더")
                .subheading("부제목")
                .privacy(Privacy.ALL)
                .thumbnailImage(image)
                .build();

        //when
        CreateFolderResponse response = CreateFolderResponse.from(folder);
        given(folderService.createFolder(any(), any())).willReturn(response);

        ResultActions result = this.mockMvc.perform(
                multipart("/api/v1/folders")
                        .file(thumbnailImage)
                        .param("name", request.getName())
                        .param("subheading", request.getSubheading())
                        .param("privacy", request.getPrivacy())
                        .param("memberId", member.getId())
                        .contentType(MediaType.MULTIPART_FORM_DATA)
                        .characterEncoding("UTF-8")
        );

        //then
        result.andExpect(status().isCreated())
                .andDo(print())
                .andDo(document("create-folder",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        requestParts(
                                partWithName("thumbnailImage").description("썸네일 이미지").optional()
                        ),
                        requestParameters(
                                parameterWithName("name").description("폴더 이름"),
                                parameterWithName("subheading").description("부제목").optional(),
                                parameterWithName("privacy").description("공개 설정"),
                                parameterWithName("memberId").description("회원 id")
                        ),
                        responseFields(
                                fieldWithPath("folderId").type(JsonFieldType.NUMBER).description("폴더 id")
                        )
                ));

    }

    @Test
    public void 전체_폴더_조회() throws Exception {

        //given
        Image image = new Image("테스트.jpg","test.jpg");
        Folder folder1 = Folder.builder()
                .id(1L)
                .name("기본 폴더1")
                .subheading("부제목1")
                .thumbnailImage(image)
                .build();

        Folder folder2 = Folder.builder()
                .id(2L)
                .name("기본 폴더2")
                .subheading("부제목2")
                .thumbnailImage(image)
                .build();

        member.addFolder(folder1);
        member.addFolder(folder2);

        List<FolderDto> data = new ArrayList<>();
        data.add(FolderDto.from(folder1));
        data.add(FolderDto.from(folder2));

        //when
        ListAllFolderResponse response = ListAllFolderResponse.from(data);
        given(folderService.listAllFolder(member.getId())).willReturn(response);

        ResultActions result = this.mockMvc.perform(
                get("/api/v1/folders/{memberId}",member.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        );

        //then
        result.andExpect(status().isOk())
                .andDo(document("list-folder",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("memberId").description("회원 id")
                        ),
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("폴더 개수"),
                                fieldWithPath("data[].folderId").type(JsonFieldType.NUMBER).description("폴더 id"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("폴더 이름"),
                                fieldWithPath("data[].subheading").type(JsonFieldType.STRING).description("폴더 부제목"),
                                fieldWithPath("data[].thumbnailImage").type(JsonFieldType.STRING).description("폴더 썸네일"),
                                fieldWithPath("data[].postCount").type(JsonFieldType.NUMBER).description("게시글 개수")
                        )
                ));
    }

}