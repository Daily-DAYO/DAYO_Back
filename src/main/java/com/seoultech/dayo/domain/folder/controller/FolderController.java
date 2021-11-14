package com.seoultech.dayo.domain.folder.controller;

import com.seoultech.dayo.domain.folder.controller.dto.request.CreateFolderRequest;
import com.seoultech.dayo.domain.folder.controller.dto.response.CreateFolderResponse;
import com.seoultech.dayo.domain.folder.controller.dto.response.ListAllFolderResponse;
import com.seoultech.dayo.domain.folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folders")
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public ResponseEntity<CreateFolderResponse> createFolder(CreateFolderRequest request) {
        return ResponseEntity.ok()
                    .body(folderService.createFolder(request));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ListAllFolderResponse> listAllFolder(@PathVariable String memberId) {
        return ResponseEntity.ok()
                    .body(folderService.listAllFolder(memberId));
    }
}
