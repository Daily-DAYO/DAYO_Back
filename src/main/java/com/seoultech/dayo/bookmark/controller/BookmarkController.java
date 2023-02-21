package com.seoultech.dayo.bookmark.controller;

import com.seoultech.dayo.bookmark.controller.dto.request.CreateBookmarkRequest;
import com.seoultech.dayo.bookmark.controller.dto.response.CreateBookmarkResponse;
import com.seoultech.dayo.bookmark.controller.dto.response.ListAllBookmarkPostResponse;
import com.seoultech.dayo.bookmark.controller.dto.response.ListAllMyBookmarkPostResponse;
import com.seoultech.dayo.bookmark.service.BookmarkService;
import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.service.PostService;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
public class BookmarkController {

  private final BookmarkService bookmarkService;
  private final MemberService memberService;
  private final PostService postService;

  @PostMapping
  public ResponseEntity<CreateBookmarkResponse> createBookmark(
      @ApiIgnore @LoginUser String memberId,
      @RequestBody @Valid CreateBookmarkRequest request) {
    Member member = memberService.findMemberById(memberId);
    Post post = postService.findPostById(request.getPostId());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(bookmarkService.createBookmark(member, post, request));
  }

  @PostMapping("/delete/{postId}")
  public ResponseEntity<Void> deleteBookmark(@ApiIgnore @LoginUser String memberId,
      @PathVariable Long postId) {
    Member member = memberService.findMemberById(memberId);
    Post post = postService.findPostById(postId);

    bookmarkService.deleteBookmark(member, post);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/list/{memberId}")
  public ResponseEntity<ListAllBookmarkPostResponse> listAllBookmarkPost(
      @PathVariable @Valid String memberId,
      @RequestParam(value = "end") String end) {

    Member member = memberService.findMemberById(memberId);
    Set<String> blockList = postService.getBlockList(member);

    return ResponseEntity.ok()
        .body(bookmarkService.listAllBookmarkPost(member, blockList, Long.valueOf(end)));
  }

  @GetMapping("/list")
  public ResponseEntity<ListAllMyBookmarkPostResponse> listAllMyBookmarkPost(
      @ApiIgnore @LoginUser String memberId,
      @RequestParam(value = "end") String end) {
    Member member = memberService.findMemberById(memberId);
    Set<String> blockList = postService.getBlockList(member);

    return ResponseEntity.ok()
        .body(bookmarkService.listAllMyBookmarkPost(member, blockList, Long.valueOf(end)));
  }

}