package com.seoultech.dayo.post.controller;


import com.seoultech.dayo.config.login.LoginUser;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/api/v2/posts")
@RequiredArgsConstructor
public class PostControllerV2 {

  private final PostService postService;
  private final MemberService memberService;
  private final FolderService folderService;

  @GetMapping
  public ResponseEntity<ListAllPostResponse> listAllPost(@ApiIgnore @LoginUser String memberId,
                                                         @RequestParam(value = "end") String end) {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.ok()
            .body(postService.listPostAll(member, Long.valueOf(end)));
  }

  @GetMapping("/category/{category}")
  public ResponseEntity<ListCategoryPostResponse> listPostByCategory(
          @ApiIgnore @LoginUser String memberId,
          @PathVariable @Valid String category,
          @RequestParam(value = "end") String end) {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.ok()
            .body(postService.listPostByCategory(member, category, Long.valueOf(end)));
  }

  @GetMapping("/feed/list")
  public ResponseEntity<ListFeedResponse> listFeed(@ApiIgnore @LoginUser String memberId,
                                                   @RequestParam(value = "end") String end) {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.ok()
            .body(postService.listFeed(member, Long.valueOf(end)));
  }

}
