package com.seoultech.dayo.folder.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.seoultech.dayo.folder.Folder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class FolderRepositoryTests {

  @Autowired
  FolderRepository folderRepository;


}