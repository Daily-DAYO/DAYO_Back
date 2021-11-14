package com.seoultech.dayo.domain.folder.service;

import com.seoultech.dayo.domain.folder.Folder;
import com.seoultech.dayo.domain.folder.controller.dto.FolderDto;
import com.seoultech.dayo.domain.folder.controller.dto.request.CreateFolderRequest;
import com.seoultech.dayo.domain.folder.controller.dto.response.CreateFolderResponse;
import com.seoultech.dayo.domain.folder.controller.dto.response.ListAllFolderResponse;
import com.seoultech.dayo.domain.folder.repository.FolderRepository;
import com.seoultech.dayo.domain.member.Member;
import com.seoultech.dayo.domain.member.repository.MemberRepository;
import com.seoultech.dayo.exception.NotExistFolderException;
import com.seoultech.dayo.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class FolderService {

    private final FolderRepository folderRepository;
    private final MemberRepository memberRepository;

    public CreateFolderResponse createFolder(CreateFolderRequest request) {

        Folder folder = request.toEntity();
        Folder savedFolder = folderRepository.save(folder);
        Optional<Member> memberOptional = memberRepository.findById(request.getMemberId());
        Member member = memberOptional.orElseThrow(NotExistMemberException::new);
        member.addFolder(savedFolder);
        return CreateFolderResponse.from(savedFolder);

    }

    public ListAllFolderResponse listAllFolder(String memberId) {
        Optional<Member> memberOptional = memberRepository.findMemberByIdWithJoin(memberId);
        Member member = memberOptional.orElseThrow(NotExistFolderException::new);
        List<Folder> folders = member.getFolders();
        List<FolderDto> collect = folders.stream()
                .map(FolderDto::from)
                .collect(toList());

        return ListAllFolderResponse.from(collect);
    }


}
