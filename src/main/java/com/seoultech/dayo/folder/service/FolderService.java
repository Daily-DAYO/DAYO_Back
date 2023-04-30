package com.seoultech.dayo.folder.service;

import static com.seoultech.dayo.folder.Privacy.ONLY_ME;
import static java.util.stream.Collectors.toList;

import com.seoultech.dayo.exception.NotExistFolderException;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.folder.Privacy;
import com.seoultech.dayo.folder.controller.dto.FolderDetailDto;
import com.seoultech.dayo.folder.controller.dto.FolderDto;
import com.seoultech.dayo.folder.controller.dto.MyFolderDto;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderInPostRequest;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderRequest;
import com.seoultech.dayo.folder.controller.dto.request.EditFolderRequest;
import com.seoultech.dayo.folder.controller.dto.request.EditOrderFolderRequest;
import com.seoultech.dayo.folder.controller.dto.request.EditOrderFolderRequest.EditOrderDto;
import com.seoultech.dayo.folder.controller.dto.response.CreateFolderInPostResponse;
import com.seoultech.dayo.folder.controller.dto.response.CreateFolderResponse;
import com.seoultech.dayo.folder.controller.dto.response.DetailFolderResponse;
import com.seoultech.dayo.folder.controller.dto.response.EditFolderResponse;
import com.seoultech.dayo.folder.controller.dto.response.ListAllFolderResponse;
import com.seoultech.dayo.folder.controller.dto.response.ListAllMyFolderResponse;
import com.seoultech.dayo.folder.repository.FolderRepository;
import com.seoultech.dayo.image.Category;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.service.ImageService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Transactional
public class FolderService {

  private final FolderRepository folderRepository;
  private final ImageService imageService;

  public CreateFolderResponse createFolder(Member member, CreateFolderRequest request)
      throws IOException {

    MultipartFile thumbnailImage = request.getThumbnailImage();
    Image image;
    if (thumbnailImage == null) {
      image = imageService.findDefaultFolderImage();
    } else {
      image = imageService.storeFile(thumbnailImage, Category.FOLDER);
    }

    request.setPrivacy(request.getPrivacy());

    Folder savedFolder = folderRepository.save(request.toEntity(image));
    List<Folder> folders = folderRepository.findFoldersByMember(member);

    savedFolder.setOrderIndex(folders.size() + 1);
    member.addFolder(savedFolder);

    return CreateFolderResponse.from(savedFolder);
  }

  public CreateFolderInPostResponse createFolderInPost(Member member,
      CreateFolderInPostRequest request) {

    Image image = imageService.findDefaultFolderImage();

    Folder folder = request.toEntity(image);
    Folder savedFolder = folderRepository.save(folder);

    List<Folder> folders = folderRepository.findFoldersByMember(member);

    savedFolder.setOrderIndex(folders.size() + 1);
    member.addFolder(savedFolder);

    return CreateFolderInPostResponse.from(savedFolder);
  }

  @Transactional(readOnly = true)
  public ListAllMyFolderResponse listAllMyFolder(Member member, Long end) {
    List<Folder> folders = folderRepository.findFoldersByMemberOrderByOrderIndex(member);

    boolean last = false;
    int size = folders.size();
    if (size <= end + 10) {
      last = true;
    }

    List<MyFolderDto> collect = folders.stream()
        .map(MyFolderDto::from)
        .skip(end)
        .limit(10)
        .collect(toList());

    return ListAllMyFolderResponse.from(collect, last);
  }

  @Transactional(readOnly = true)
  public ListAllFolderResponse listAllFolder(Member member, Long end) {
    List<Folder> folders = folderRepository.findFoldersByMemberOrderByOrderIndex(member);

    boolean last = false;
    int size = folders.size();
    if (size <= end + 10) {
      last = true;
    }

    List<FolderDto> collect = folders.stream()
        .filter(folder -> folder.getPrivacy() != ONLY_ME)
        .map(FolderDto::from)
        .skip(end)
        .limit(10)
        .collect(toList());

    return ListAllFolderResponse.from(collect, last);
  }

  //TODO 리팩토링
  public EditFolderResponse editFolder(EditFolderRequest request) throws IOException {

    Folder folder = findFolderById(request.getFolderId());

    if (StringUtils.hasText(request.getName())) {
      folder.setName(request.getName());
    }
    if (StringUtils.hasText(request.getSubheading())) {
      folder.setSubheading(request.getSubheading());
    }
    if (StringUtils.hasText(request.getPrivacy())) {
      folder.setPrivacy(Privacy.valueOf(request.getPrivacy()));
    }

    // 기본이미지
    if (request.getThumbnailImage() == null && request.getIsFileChange()) {
      folder.setThumbnailImage(imageService.findDefaultFolderImage());
    }

    // 변경이미지
    if (request.getThumbnailImage() != null && request.getIsFileChange()) {
      Image image = imageService.storeFile(request.getThumbnailImage(), Category.FOLDER);
      folder.setThumbnailImage(image);
    }

    return EditFolderResponse.from(folder);
  }

  public void orderFolder(Member member, EditOrderFolderRequest.EditOrderDto[] request) {

    List<Folder> folders = folderRepository.findFoldersByMember(member);

    //TODO 리팩토링
    for (Folder folder : folders) {
      for (EditOrderDto editOrderDto : request) {
        if (folder.getId().equals(editOrderDto.getFolderId())) {
          folder.setOrderIndex(editOrderDto.getOrderIndex());
          break;
        }
      }
    }
  }

  public void deleteFolder(Long folderId) {
    folderRepository.deleteById(folderId);
  }

  @Transactional(readOnly = true)
  public DetailFolderResponse detailFolder(Long folderId) {

    Folder folder = findFolderById(folderId);

    List<Post> posts = folder.getPosts();

    List<FolderDetailDto> collect = posts.stream()
        .map(FolderDetailDto::from)
        .sorted((a1, a2) -> a2.getCreateDate().compareTo(a1.getCreateDate()))
        .collect(toList());

    return DetailFolderResponse.from(folder, collect);
  }

  public Folder createDefaultFolder() {
    Image image = imageService.findDefaultFolderImage();
    Folder folder = new Folder("기본 폴더", "폴더 소개를 적어주세요", Privacy.ALL, image);
    return folderRepository.save(folder);
  }

  public Folder findFolderById(Long folderId) {
    return folderRepository.findById(folderId)
        .orElseThrow(NotExistFolderException::new);
  }

  public boolean checkMyFolder(Member member, Folder folder) {

    List<Folder> folders = folderRepository.findFoldersByMember(member);
    return folders.contains(folder);

  }

  public void deleteAllByMember(Member member) {
    folderRepository.deleteAllByMember(member);
  }

}
