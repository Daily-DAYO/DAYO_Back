package com.seoultech.dayo.folder.service;

import com.seoultech.dayo.exception.NotExistFolderException;
import com.seoultech.dayo.exception.NotExistMemberException;
import com.seoultech.dayo.folder.Privacy;
import com.seoultech.dayo.folder.controller.dto.FolderDetailDto;
import com.seoultech.dayo.folder.controller.dto.MyFolderDto;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderInPostRequest;
import com.seoultech.dayo.folder.controller.dto.request.EditFolderRequest;
import com.seoultech.dayo.folder.controller.dto.response.*;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.repository.ImageRepository;
import com.seoultech.dayo.image.service.ImageService;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.folder.controller.dto.FolderDto;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderRequest;
import com.seoultech.dayo.folder.repository.FolderRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static com.seoultech.dayo.folder.Privacy.ONLY_ME;
import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class FolderService {

    private final FolderRepository folderRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    public CreateFolderResponse createFolder(String memberId, CreateFolderRequest request) throws IOException {

        MultipartFile thumbnailImage = request.getThumbnailImage();
        Image image;
        if(thumbnailImage == null) {
            int randomValue = (int)(Math.random() * 5) + 1;
            image = imageRepository.findById((long) randomValue).get();
        } else {
            image = imageService.storeFile(thumbnailImage);
        }

        Folder savedFolder = folderRepository.save(request.toEntity(image));

        Member member = findMember(memberId);
        member.addFolder(savedFolder);

        return CreateFolderResponse.from(savedFolder);
    }

    public CreateFolderInPostResponse createFolderInPost(String memberId, CreateFolderInPostRequest request) {

        Image image = imageRepository.findById(1L).get();

        Folder folder = request.toEntity();
        folder.addThumbnailImage(image);

        Folder savedFolder = folderRepository.save(folder);

        Member member = findMember(memberId);
        member.addFolder(savedFolder);

        return CreateFolderInPostResponse.from(savedFolder);
    }

    @Transactional(readOnly = true)
    public ListAllMyFolderResponse listAllMyFolder(String memberId) {
        Member member = findMemberWithFolderJoin(memberId);
        List<Folder> folders = member.getFolders();
        List<MyFolderDto> collect = folders.stream()
                .map(MyFolderDto::from)
                .collect(toList());

        return ListAllMyFolderResponse.from(collect);
    }

    @Transactional(readOnly = true)
    public ListAllFolderResponse listAllFolder(String memberId) {
        Member member = findMemberWithFolderJoin(memberId);
        List<Folder> folders = member.getFolders();
        List<FolderDto> collect = folders.stream()
                .filter(folder -> folder.getPrivacy() != ONLY_ME)
                .map(FolderDto::from)
                .collect(toList());

        return ListAllFolderResponse.from(collect);
    }

    public EditFolderResponse editFolder(EditFolderRequest request) throws IOException {

        Folder folder = findFolder(request.getFolderId());

        if (StringUtils.hasText(request.getName()))
            folder.setName(request.getName());
        if (StringUtils.hasText(request.getSubheading()))
            folder.setSubheading(request.getSubheading());
        if (StringUtils.hasText(request.getPrivacy()))
            folder.setPrivacy(Privacy.valueOf(request.getPrivacy()));
        if (request.getThumbnailImage() != null) {
            Image image = imageService.storeFile(request.getThumbnailImage());
            folder.setThumbnailImage(image);
        }

        return EditFolderResponse.from(folder);
    }

    public void deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
    }

    @Transactional(readOnly = true)
    public DetailFolderResponse detailFolder(Long folderId) {

        Folder folder = findFolder(folderId);

        List<FolderDetailDto> collect = folder.getPosts().stream()
                .map(FolderDetailDto::from)
                .collect(toList());

        return DetailFolderResponse.from(folder, collect);
    }

    private Folder findFolder(Long folderId) {
        return folderRepository.findById(folderId)
                .orElseThrow(NotExistFolderException::new);
    }

    private Member findMemberWithFolderJoin(String memberId) {
        return memberRepository.findMemberByIdWithJoin(memberId)
                .orElseThrow(NotExistMemberException::new);
    }

    private Member findMember(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(NotExistMemberException::new);
    }
}
