package com.seoultech.dayo.folder.controller;

import com.seoultech.dayo.folder.controller.dto.FolderDto;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderRequest;
import com.seoultech.dayo.folder.controller.dto.response.CreateFolderResponse;
import com.seoultech.dayo.folder.controller.dto.response.ListAllFolderResponse;
import com.seoultech.dayo.folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folders")
public class FolderController {

    private final FolderService folderService;

    @PostMapping
    public ResponseEntity<CreateFolderResponse> createFolder(@ModelAttribute @Valid CreateFolderRequest request) throws IOException {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(folderService.createFolder(request));
    }

    @GetMapping("/{memberId}")
    public ResponseEntity<ListAllFolderResponse> listAllFolder(@PathVariable String memberId) {
        return ResponseEntity.ok()
                .body(folderService.listAllFolder(memberId));
    }
}
