package com.seoultech.dayo.folder.controller;

import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderInPostRequest;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderRequest;
import com.seoultech.dayo.folder.controller.dto.request.EditFolderRequest;
import com.seoultech.dayo.folder.controller.dto.request.EditOrderFolderRequest;
import com.seoultech.dayo.folder.controller.dto.response.*;
import com.seoultech.dayo.folder.service.FolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folders")
@Slf4j
public class FolderController {

    private final FolderService folderService;
    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<CreateFolderResponse> createFolder(HttpServletRequest servletRequest, @ModelAttribute @Valid CreateFolderRequest request) throws IOException {
        String memberId = getDataInToken(servletRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(folderService.createFolder(memberId, request));
    }

    @PostMapping("/inPost")
    public ResponseEntity<CreateFolderInPostResponse> createFolderInPost(HttpServletRequest servletRequest, @RequestBody @Valid CreateFolderInPostRequest request) {
        String memberId = getDataInToken(servletRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(folderService.createFolderInPost(memberId, request));
    }

    @PostMapping("/delete/{folderId}")
    public ResponseEntity<Void> deleteFolder(@PathVariable @Valid Long folderId) {
        folderService.deleteFolder(folderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/order")
    public ResponseEntity<Void> orderFolder(HttpServletRequest servletRequest, @RequestBody EditOrderFolderRequest.EditOrderDto[] request) {
        String memberId = getDataInToken(servletRequest);
        folderService.orderFolder(memberId, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/patch")
    public ResponseEntity<EditFolderResponse> editFolder(@ModelAttribute EditFolderRequest request) throws IOException {
        log.info(request.toString());
        return ResponseEntity.ok()
                .body(folderService.editFolder(request));
    }

    @GetMapping("/my")
    public ResponseEntity<ListAllMyFolderResponse> listAllMyFolder(HttpServletRequest servletRequest) {
        String memberId = getDataInToken(servletRequest);
        return ResponseEntity.ok()
                .body(folderService.listAllMyFolder(memberId));
    }

    @GetMapping("/list/{memberId}")
    public ResponseEntity<ListAllFolderResponse> listAllFolder(@PathVariable String memberId) {
        return ResponseEntity.ok()
                .body(folderService.listAllFolder(memberId));
    }

    @GetMapping("/{folderId}")
    public ResponseEntity<DetailFolderResponse> detailListFolder(@PathVariable Long folderId) {
        return ResponseEntity.ok()
                .body(folderService.detailFolder(folderId));
    }

    private String getDataInToken(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization").substring(7);
        return tokenProvider.getDataFromToken(token);
    }
}
