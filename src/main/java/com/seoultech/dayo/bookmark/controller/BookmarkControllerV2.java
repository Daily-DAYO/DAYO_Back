package com.seoultech.dayo.bookmark.controller;

import com.seoultech.dayo.bookmark.controller.dto.request.DeleteBookmarkRequest;
import com.seoultech.dayo.bookmark.service.BookmarkServiceV2;
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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.Set;

@Tag(name = "Bookmark", description = "북마크 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/bookmark")
public class BookmarkControllerV2 {

  private final BookmarkServiceV2 bookmarkService;
  private final MemberService memberService;

  @Tag(name = "Comments")
  @Operation(summary = "북마크 삭제")
  @ApiResponses({
          @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
          @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping("/delete")
  public ResponseEntity<Void> deleteBookmark(@ApiIgnore @LoginUser String memberId,
                             @RequestBody DeleteBookmarkRequest request) {
    Member member = memberService.findMemberById(memberId);
    bookmarkService.deleteBookmark(member, request);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}