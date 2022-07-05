package com.seoultech.dayo.folder.controller;

import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderInPostRequest;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderRequest;
import com.seoultech.dayo.folder.controller.dto.request.EditFolderRequest;
import com.seoultech.dayo.folder.controller.dto.request.EditOrderFolderRequest;
import com.seoultech.dayo.folder.controller.dto.response.*;
import com.seoultech.dayo.folder.service.FolderService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<CreateFolderResponse> createFolder(HttpServletRequest servletRequest,
      @ModelAttribute @Valid CreateFolderRequest request) throws IOException {
    String memberId = servletRequest.getAttribute("memberId").toString();

    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(folderService.createFolder(member, request));
  }

  @PostMapping("/inPost")
  public ResponseEntity<CreateFolderInPostResponse> createFolderInPost(
      HttpServletRequest servletRequest, @RequestBody @Valid CreateFolderInPostRequest request) {
    String memberId = servletRequest.getAttribute("memberId").toString();

    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(folderService.createFolderInPost(member, request));
  }

  @PostMapping("/delete/{folderId}")
  public ResponseEntity<Void> deleteFolder(@PathVariable @Valid Long folderId) {
    folderService.deleteFolder(folderId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @PostMapping("/order")
  public ResponseEntity<Void> orderFolder(HttpServletRequest servletRequest,
      @RequestBody EditOrderFolderRequest.EditOrderDto[] request) {
    String memberId = servletRequest.getAttribute("memberId").toString();

    Member member = memberService.findMemberById(memberId);
    folderService.orderFolder(member, request);

    return new ResponseEntity<>(HttpStatus.OK);
  }

  @PostMapping("/patch")
  public ResponseEntity<EditFolderResponse> editFolder(@ModelAttribute EditFolderRequest request)
      throws IOException {
    return ResponseEntity.ok()
        .body(folderService.editFolder(request));
  }

  @GetMapping("/my")
  public ResponseEntity<ListAllMyFolderResponse> listAllMyFolder(
      HttpServletRequest servletRequest) {
    String memberId = servletRequest.getAttribute("memberId").toString();

    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(folderService.listAllMyFolder(member));
  }

  @GetMapping("/list/{memberId}")
  public ResponseEntity<ListAllFolderResponse> listAllFolder(@PathVariable String memberId) {

    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(folderService.listAllFolder(member));
  }

  @GetMapping("/{folderId}")
  public ResponseEntity<DetailFolderResponse> detailListFolder(@PathVariable Long folderId) {
    return ResponseEntity.ok()
        .body(folderService.detailFolder(folderId));
  }

}
