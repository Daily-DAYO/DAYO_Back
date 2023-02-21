package com.seoultech.dayo.folder.service;

import static com.seoultech.dayo.image.Category.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.folder.Privacy;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderRequest;
import com.seoultech.dayo.folder.controller.dto.request.EditOrderFolderRequest;
import com.seoultech.dayo.folder.controller.dto.request.EditOrderFolderRequest.EditOrderDto;
import com.seoultech.dayo.folder.controller.dto.response.CreateFolderResponse;
import com.seoultech.dayo.folder.controller.dto.response.ListAllFolderResponse;
import com.seoultech.dayo.folder.repository.FolderRepository;
import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.repository.ImageRepository;
import com.seoultech.dayo.image.service.ImageService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.postHashtag.PostHashtag;
import com.seoultech.dayo.search.Search;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FolderServiceTests {

  @Mock
  FolderRepository folderRepository;

  @Mock
  ImageService imageService;

  @InjectMocks
  FolderService folderService;

  Member member;
  Post post;
  Folder folder;
  Image image;

  @BeforeEach
  void init() {
    member = new Member("조재영", "jdyj6543@naver.com");
    image = new Image("testImage", "testImage", FOLDER);
    post = new Post(member, "테스트1", image, Category.SCHEDULER, null);
    folder = new Folder(1L, "테스트폴더", "테스트폴더 부제목", Privacy.ALL, null);
  }

  @Test
  @DisplayName("폴더 생성")
  void createFolderTest() throws IOException {

    CreateFolderRequest request = new CreateFolderRequest("테스트폴더", "테스트폴더 부제목", Privacy.ALL.name(),
        null);

    given(folderRepository.save(any())).willReturn(folder);

    folderService.createFolder(member, request);

    assertThat(folder.getOrderIndex()).isEqualTo(1);
    assertThat(member.getFolders().size()).isEqualTo(1);
    assertThat(member.getFolders().get(0).getId()).isEqualTo(1L);
  }

  @Test
  @DisplayName("폴더 생성 - 이미 폴더가 있는 경우")
  void createFolderTest2() throws IOException {

    CreateFolderRequest request = new CreateFolderRequest("테스트폴더", "테스트폴더 부제목", Privacy.ALL.name(),
        null);
    List<Folder> folderList = makeFolderList();

    given(folderRepository.save(any())).willReturn(folder);
    given(folderRepository.findFoldersByMember(any())).willReturn(folderList);

    folderService.createFolder(member, request);

    assertThat(folder.getOrderIndex()).isEqualTo(5);
    assertThat(member.getFolders().size()).isEqualTo(5);
  }

  @Test
  @DisplayName("폴더 정렬")
  void orderFolderTest() {

    List<EditOrderDto> data = makeEditOrderDtoList();

    List<Folder> folderList = makeFolderList();
    given(folderRepository.findFoldersByMember(member)).willReturn(folderList);

    folderService.orderFolder(member, data.toArray(new EditOrderDto[0]));

    assertThat(member.getFolders().get(0).getOrderIndex()).isEqualTo(2);
    assertThat(member.getFolders().get(1).getOrderIndex()).isEqualTo(3);
    assertThat(member.getFolders().get(2).getOrderIndex()).isEqualTo(1);
    assertThat(member.getFolders().get(3).getOrderIndex()).isEqualTo(4);

  }

  @Test
  @DisplayName("폴더 리스트 조회")
  void listAllFolderTest() {

    List<Folder> folderList = makeFolderList();
    given(folderRepository.findFoldersByMemberOrderByOrderIndex(any())).willReturn(folderList);

    ListAllFolderResponse response = folderService.listAllFolder(member, 10L);

    assertThat(response.getCount()).isEqualTo(3);
  }

  private List<EditOrderDto> makeEditOrderDtoList() {
    List<EditOrderDto> data = new ArrayList<>();
    EditOrderDto orderDto1 = new EditOrderDto(2L, 2);
    EditOrderDto orderDto2 = new EditOrderDto(3L, 3);
    EditOrderDto orderDto3 = new EditOrderDto(4L, 1);
    EditOrderDto orderDto4 = new EditOrderDto(5L, 4);
    data.add(orderDto1);
    data.add(orderDto2);
    data.add(orderDto3);
    data.add(orderDto4);
    return data;
  }

  @NotNull
  private List<Folder> makeFolderList() {

    Image image = new Image("test", "test", FOLDER);

    List<Folder> folderList = new ArrayList<>();
    Folder folder1 = new Folder(2L, "테스트폴더", "테스트폴더 부제목", Privacy.ALL, image);
    Folder folder2 = new Folder(3L, "테스트폴더", "테스트폴더 부제목", Privacy.ALL, image);
    Folder folder3 = new Folder(4L, "테스트폴더", "테스트폴더 부제목", Privacy.ONLY_ME, image);
    Folder folder4 = new Folder(5L, "테스트폴더", "테스트폴더 부제목", Privacy.ALL, image);
    folderList.add(folder1);
    folderList.add(folder2);
    folderList.add(folder3);
    folderList.add(folder4);

    member.addFolder(folder1);
    member.addFolder(folder2);
    member.addFolder(folder3);
    member.addFolder(folder4);
    return folderList;
  }
}