package com.seoultech.dayo.domain.folder.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoultech.dayo.domain.folder.Folder;
import com.seoultech.dayo.domain.folder.controller.FolderController;
import com.seoultech.dayo.domain.folder.controller.dto.FolderDto;
import com.seoultech.dayo.domain.folder.controller.dto.response.ListAllFolderResponse;
import com.seoultech.dayo.domain.member.Member;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.seoultech.dayo.ApiDocumentUtils.getDocumentRequest;
import static com.seoultech.dayo.ApiDocumentUtils.getDocumentResponse;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(MockitoExtension.class)
@WebMvcTest(FolderController.class)
@AutoConfigureRestDocs(uriScheme = "https", uriHost = "docs.api.com")
class FolderServiceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FolderService folderService;

//    @Test
//    public void listAll() throws Exception {
//
//        //given
//        Member member = new Member("조재영", "jdyj@naver.com","");
//        Folder folder1 = Folder.builder()
//                .name("기본 폴더")
//                .subheading("")
//                .thumbnailImage(null)
//                .build();
//
//        Folder folder2 = Folder.builder()
//                .name("기본 폴더")
//                .subheading("")
//                .thumbnailImage(null)
//                .build();
//        member.addFolder(folder1);
//        member.addFolder(folder2);
//
//        List<FolderDto> data = new ArrayList<>();
//        data.add(FolderDto.from(folder1));
//        data.add(FolderDto.from(folder2));
//
//        //when
//        ListAllFolderResponse response = new ListAllFolderResponse(data.size(), data);
//        given(folderService.listAllFolder(member.getId())).willReturn(response);
//
//        ResultActions result = this.mockMvc.perform(
//                put("/api/v1/folders/", member.getId())
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON)
//        );
//
//        //then
//        result.andExpect(status().isOk())
//                .andDo(document("folder-list",
//                        getDocumentRequest(),
//                        getDocumentResponse(),
//                        pathParameters(
//                            parameterWithName("memberId").description("회원 id")
//                        ),
//                        responseFields(
//                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("폴더 개수"),
//                                fieldWithPath("data.")
//                        )
//                        ))
//
//
//    }

}