package com.seoultech.dayo.folder.repository;

import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FolderRepository extends JpaRepository<Folder, Long> {

    List<Folder> findAllByMember(Member member);

    List<Folder> findFoldersByMember(Member member);

}
