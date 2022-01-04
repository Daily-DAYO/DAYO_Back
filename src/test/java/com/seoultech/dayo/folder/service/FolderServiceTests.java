package com.seoultech.dayo.folder.service;

import com.seoultech.dayo.folder.repository.FolderRepository;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.repository.ImageRepository;
import com.seoultech.dayo.image.service.ImageService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FolderServiceTests {

    @Mock
    private ImageRepository imageRepository;

    @Mock
    private FolderRepository folderRepository;

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ImageService imageService;

    @InjectMocks
    private FolderService folderService;

    private Member member;

    @BeforeEach
    void init() {
        member = new Member("테스트", "jdyj@naver.com", "test.jpg");
    }

//    @Test
//    @DisplayName("폴더 생성")
//    void createFolderTest() throws Exception {
//
//        //given
//        Image image = new Image("test.jpg", "test.jpg");
//        given(memberRepository.findById(any())).willReturn(Optional.of(member));
//        given(imageService.storeFile(any())).willReturn(image);
//    }

}