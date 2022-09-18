package com.seoultech.dayo.folder.controller;

import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.config.login.LoginUser;
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
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folders")
@Slf4j
public class FolderController {

  private final FolderService folderService;
  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<CreateFolderResponse> createFolder(@ApiIgnore @LoginUser String memberId,
      @ModelAttribute @Valid CreateFolderRequest request) throws IOException {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(folderService.createFolder(member, request));
  }

  @PostMapping("/inPost")
  public ResponseEntity<CreateFolderInPostResponse> createFolderInPost(
      @ApiIgnore @LoginUser String memberId,
      @RequestBody @Valid CreateFolderInPostRequest request) {
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
  public ResponseEntity<Void> orderFolder(@ApiIgnore @LoginUser String memberId,
      @RequestBody EditOrderFolderRequest.EditOrderDto[] request) {
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
      @ApiIgnore @LoginUser String memberId) {
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
