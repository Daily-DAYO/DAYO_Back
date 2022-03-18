package com.seoultech.dayo.post.controller;


import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.exception.InvalidFolderAccess;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.folder.service.FolderService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.controller.dto.request.CreatePostRequest;
import com.seoultech.dayo.post.controller.dto.request.EditPostRequest;
import com.seoultech.dayo.post.controller.dto.response.*;
import com.seoultech.dayo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;
  private final MemberService memberService;
  private final FolderService folderService;

  @GetMapping("/dayopick/all")
  public ResponseEntity<DayoPickPostListResponse> dayoPickListAll(
      HttpServletRequest servletRequest) {

    String memberId = servletRequest.getAttribute("memberId").toString();
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(postService.dayoPickAllList(member));
  }

  @GetMapping("/dayopick/{category}")
  public ResponseEntity<DayoPickPostListResponse> dayoPickList(HttpServletRequest servletRequest,
      @PathVariable @Valid String category) {

    String memberId = servletRequest.getAttribute("memberId").toString();
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(postService.dayoPickListWithCategory(member, category));
  }

  @PostMapping("/{postId}/edit")
  public ResponseEntity<EditPostResponse> editPost(HttpServletRequest servletRequest,
      @RequestBody EditPostRequest request, @PathVariable Long postId) {

    String memberId = servletRequest.getAttribute("memberId").toString();
    Member member = memberService.findMemberById(memberId);

    if (request.getFolderId() != null) {
      Folder folder = folderService.findFolderById(request.getFolderId());
      return ResponseEntity.ok()
          .body(postService.editPost(request, member, folder, postId));
    }

    return ResponseEntity.ok()
        .body(postService.editPost(request, member, null, postId));
  }

  @GetMapping
  public ResponseEntity<ListAllPostResponse> listAllPost(HttpServletRequest servletRequest) {
    String memberId = servletRequest.getAttribute("memberId").toString();
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(postService.listPostAll(member));
  }

  @GetMapping("/category/{category}")
  public ResponseEntity<ListCategoryPostResponse> listPostByCategory(
      HttpServletRequest servletRequest,
      @PathVariable @Valid String category) {

    String memberId = servletRequest.getAttribute("memberId").toString();
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(postService.listPostByCategory(member, category));
  }

  @PostMapping
  public ResponseEntity<CreatePostResponse> createPost(HttpServletRequest servletRequest,
      @ModelAttribute @Valid CreatePostRequest request) throws IOException {

    String memberId = servletRequest.getAttribute("memberId").toString();
    Member member = memberService.findMemberById(memberId);

    Folder folder = folderService.findFolderById(request.getFolderId());

    if (folderService.checkMyFolder(member, folder)) {
      return ResponseEntity.ok()
          .body(postService.createPost(member, folder, request));
    } else {
      throw new InvalidFolderAccess();
    }

  }

  @PostMapping("/delete/{postId}")
  public ResponseEntity<Void> deletePost(HttpServletRequest servletRequest,
      @PathVariable Long postId) {
    String memberId = servletRequest.getAttribute("memberId").toString();

    postService.deletePost(memberId, postId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/{postId}")
  public ResponseEntity<DetailPostResponse> detailPost(HttpServletRequest servletRequest,
      @PathVariable @Valid Long postId) {
    String memberId = servletRequest.getAttribute("memberId").toString();

    return ResponseEntity.ok()
        .body(postService.detailPost(memberId, postId));
  }

  @GetMapping("/feed/list")
  public ResponseEntity<ListFeedResponse> listFeed(HttpServletRequest servletRequest) {

    String memberId = servletRequest.getAttribute("memberId").toString();
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(postService.listFeed(member));
  }

}
