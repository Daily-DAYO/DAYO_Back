package com.seoultech.dayo.domain.folder.controller;

import com.seoultech.dayo.domain.folder.controller.dto.request.CreateFolderRequest;
import com.seoultech.dayo.domain.folder.controller.dto.response.CreateFolderResponse;
import com.seoultech.dayo.domain.folder.controller.dto.response.ListAllFolderResponse;
import com.seoultech.dayo.domain.folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folders")
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public ResponseEntity<CreateFolderResponse> createFolder(MultipartHttpServletRequest servletRequest) throws IOException {
        return ResponseEntity.ok()
                    .body(folderService.createFolder(servletRequest));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ListAllFolderResponse> listAllFolder(@PathVariable String memberId) {
        return ResponseEntity.ok()
                    .body(folderService.listAllFolder(memberId));
    }
}
