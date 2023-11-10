package com.seoultech.dayo.folder.controller;

import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.exception.dto.NotFoundFailResponse;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderInPostRequest;
import com.seoultech.dayo.folder.controller.dto.request.CreateFolderRequest;
import com.seoultech.dayo.folder.controller.dto.request.EditFolderRequest;
import com.seoultech.dayo.folder.controller.dto.request.EditOrderFolderRequest;
import com.seoultech.dayo.folder.controller.dto.response.CreateFolderInPostResponse;
import com.seoultech.dayo.folder.controller.dto.response.CreateFolderResponse;
import com.seoultech.dayo.folder.controller.dto.response.DetailFolderResponse;
import com.seoultech.dayo.folder.controller.dto.response.EditFolderResponse;
import com.seoultech.dayo.folder.controller.dto.response.FolderInfoResponse;
import com.seoultech.dayo.folder.controller.dto.response.ListAllFolderResponse;
import com.seoultech.dayo.folder.controller.dto.response.ListAllMyFolderResponse;
import com.seoultech.dayo.folder.service.FolderService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "Folder", description = "폴더 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/folders")
@Slf4j
public class FolderController {

  private final FolderService folderService;
  private final MemberService memberService;

  @Tag(name = "Folder")
  @Operation(summary = "폴더 생성")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "폴더 생성 성공", content = @Content(schema = @Schema(implementation = CreateFolderResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping
  public ResponseEntity<CreateFolderResponse> createFolder(@ApiIgnore @LoginUser String memberId,
      @ModelAttribute @Valid CreateFolderRequest request) throws IOException {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(folderService.createFolder(member, request));
  }

  @Tag(name = "Folder")
  @Operation(summary = "폴더 생성", description = "게시글 만들 때 폴더를 생성합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "폴더 생성 성공", content = @Content(schema = @Schema(implementation = CreateFolderInPostResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
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
  public ResponseEntity<DetailFolderResponse> detailListFolder(@PathVariable Long folderId,
      @RequestParam(value = "end") String end) {
    return ResponseEntity.ok()
        .body(folderService.detailFolder(folderId, Long.valueOf(end)));
  }

  @GetMapping("/{folderId}/info")
  public ResponseEntity<FolderInfoResponse> folderInfo(@PathVariable Long folderId) {
    return ResponseEntity.ok()
        .body(folderService.folderInfo(folderId));
  }

}
