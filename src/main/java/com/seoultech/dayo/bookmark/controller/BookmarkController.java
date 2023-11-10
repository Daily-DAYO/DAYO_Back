package com.seoultech.dayo.bookmark.controller;

import com.seoultech.dayo.bookmark.controller.dto.request.CreateBookmarkRequest;
import com.seoultech.dayo.bookmark.controller.dto.response.CreateBookmarkResponse;
import com.seoultech.dayo.bookmark.controller.dto.response.ListAllBookmarkPostResponse;
import com.seoultech.dayo.bookmark.controller.dto.response.ListAllMyBookmarkPostResponse;
import com.seoultech.dayo.bookmark.service.BookmarkService;
import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.exception.dto.NotFoundFailResponse;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.service.PostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Set;
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

@Tag(name = "Bookmark", description = "북마크 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bookmark")
public class BookmarkController {

  private final BookmarkService bookmarkService;
  private final MemberService memberService;
  private final PostService postService;

  @Tag(name = "Bookmark")
  @Operation(summary = "북마크 생성", description = "postId를 넣어 해당 게시글을 북마크합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(schema = @Schema(implementation = CreateBookmarkResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping
  public ResponseEntity<CreateBookmarkResponse> createBookmark(
      @ApiIgnore @LoginUser String memberId,
      @RequestBody @Valid CreateBookmarkRequest request) {
    Member member = memberService.findMemberById(memberId);
    Post post = postService.findPostById(request.getPostId());

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(bookmarkService.createBookmark(member, post, request));
  }

  @Tag(name = "Bookmark")
  @Operation(summary = "북마크 삭제", description = "postId를 넣어 해당 게시글 북마크를 삭제합니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping("/delete/{postId}")
  public ResponseEntity<Void> deleteBookmark(@ApiIgnore @LoginUser String memberId,
      @PathVariable Long postId) {
    Member member = memberService.findMemberById(memberId);
    Post post = postService.findPostById(postId);

    bookmarkService.deleteBookmark(member, post);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Tag(name = "Bookmark")
  @Operation(summary = "북마크 조회(다른 사용자)", description = "다른 사용자의 북마크 리스트를 조회합니다. 차단한 사용자의 게시글은 보이지 않습니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "북마크 조회 성공", content = @Content(schema = @Schema(implementation = ListAllBookmarkPostResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @GetMapping("/list/{memberId}")
  public ResponseEntity<ListAllBookmarkPostResponse> listAllBookmarkPost(
      @PathVariable @Valid String memberId,
      @RequestParam(value = "end") String end) {

    Member member = memberService.findMemberById(memberId);
    Set<String> blockList = postService.getBlockList(member);

    return ResponseEntity.ok()
        .body(bookmarkService.listAllBookmarkPost(member, blockList, Long.valueOf(end)));
  }

  @Tag(name = "Bookmark")
  @Operation(summary = "북마크 조회(본인)", description = "본인의 북마크 리스트를 조회합니다. 차단한 사용자의 게시글은 보이지 않습니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "북마크 조회 성공", content = @Content(schema = @Schema(implementation = ListAllMyBookmarkPostResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
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