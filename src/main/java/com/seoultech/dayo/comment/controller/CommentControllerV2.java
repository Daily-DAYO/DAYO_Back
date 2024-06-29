package com.seoultech.dayo.comment.controller;


import com.seoultech.dayo.comment.controller.dto.request.CreateCommentRequestV2;
import com.seoultech.dayo.comment.controller.dto.response.CreateCommentResponse;
import com.seoultech.dayo.comment.service.CommentService;
import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.exception.dto.NotFoundFailResponse;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
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

@Tag(name = "Comments", description = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/comments")
public class CommentControllerV2 {

  private final CommentService commentService;
  private final MemberService memberService;

  @Tag(name = "Comments")
  @Operation(summary = "댓글 생성")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(schema = @Schema(implementation = CreateCommentResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping
  public ResponseEntity<CreateCommentResponse> createComment(@ApiIgnore @LoginUser String memberId,
      @RequestBody CreateCommentRequestV2 request) {
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(commentService.createCommentV2(member, request));
  }


}
