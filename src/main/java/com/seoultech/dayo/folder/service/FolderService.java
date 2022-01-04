package com.seoultech.dayo.folder.service;

import com.seoultech.dayo.exception.NotExistMemberException;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.repository.ImageRepository;
import com.seoultech.dayo.image.service.ImageService;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.folder.controller.dto.FolderDto;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderRequest;
import com.seoultech.dayo.folder.controller.dto.response.CreateFolderResponse;
import com.seoultech.dayo.folder.controller.dto.response.ListAllFolderResponse;
import com.seoultech.dayo.folder.repository.FolderRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final MemberRepository memberRepository;
    private final ImageRepository imageRepository;
    private final ImageService imageService;

    @Transactional
    public CreateFolderResponse createFolder(CreateFolderRequest request) throws IOException {

        MultipartFile thumbnailImage = request.getThumbnailImage();
        Image image;
        if(thumbnailImage == null) {
            int randomValue = (int)(Math.random() * 5) + 1;
            image = imageRepository.findById((long) randomValue).get();
        } else {
            image = imageService.storeFile(thumbnailImage);
        }


        Folder savedFolder = folderRepository.save(request.toEntity(image));

        Optional<Member> memberOptional = memberRepository.findById(request.getMemberId());
        Member member = memberOptional.orElseThrow(NotExistMemberException::new);
        member.addFolder(savedFolder);

        return CreateFolderResponse.from(savedFolder);
    }

    public ListAllFolderResponse listAllFolder(String memberId) {
        Optional<Member> memberOptional = memberRepository.findMemberByIdWithJoin(memberId);
        Member member = memberOptional.orElseThrow(NotExistMemberException::new);
        List<Folder> folders = member.getFolders();
        List<FolderDto> collect = folders.stream()
                .map(FolderDto::from)
                .collect(toList());

        return ListAllFolderResponse.from(collect);
    }

}
