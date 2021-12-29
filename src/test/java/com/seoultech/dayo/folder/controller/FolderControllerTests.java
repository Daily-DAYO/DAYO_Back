package com.seoultech.dayo.folder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.folder.controller.dto.FolderDto;
import com.seoultech.dayo.folder.controller.dto.response.ListAllFolderResponse;
import com.seoultech.dayo.folder.repository.FolderRepository;
import com.seoultech.dayo.folder.service.FolderService;
import com.seoultech.dayo.image.repository.ImageRepository;
import com.seoultech.dayo.image.service.ImageService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
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

import java.util.ArrayList;
import java.util.List;

import static com.seoultech.dayo.ApiDocumentUtils.getDocumentRequest;
import static com.seoultech.dayo.ApiDocumentUtils.getDocumentResponse;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
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

    @Test
    public void listAll() throws Exception {

        //given
        Member member = new Member("조재영", "jdyj@naver.com","");
        Folder folder1 = Folder.builder()
                .name("gibon1")
                .subheading("")
                .thumbnailImage(null)
                .build();

        Folder folder2 = Folder.builder()
                .name("gibon2")
                .subheading("")
                .thumbnailImage(null)
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
                .andDo(document("folder-list",
                        getDocumentRequest(),
                        getDocumentResponse(),
                        pathParameters(
                                parameterWithName("memberId").description("회원 id")
                        ),
                        responseFields(
                                fieldWithPath("count").type(JsonFieldType.NUMBER).description("폴더 개수"),
                                fieldWithPath("data[].name").type(JsonFieldType.STRING).description("폴더 이름"),
                                fieldWithPath("data[].subheading").type(JsonFieldType.STRING).description("폴더 부제목"),
                                fieldWithPath("data[].postCount").type(JsonFieldType.NUMBER).description("게시글 개수")
                        )
                ));
    }

}