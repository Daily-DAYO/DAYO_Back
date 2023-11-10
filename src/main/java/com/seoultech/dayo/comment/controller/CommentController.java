package com.seoultech.dayo.comment.controller;


import com.seoultech.dayo.comment.controller.dto.request.CreateCommentRequest;
import com.seoultech.dayo.comment.controller.dto.response.CreateCommentResponse;
import com.seoultech.dayo.comment.controller.dto.response.ListAllCommentResponse;
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
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@Tag(name = "Comments", description = "댓글 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

  private final CommentService commentService;
  private final MemberService memberService;

  @Tag(name = "Comments")
  @Operation(summary = "댓글 생성")
  @ApiResponses({
      @ApiResponse(responseCode = "201", description = "CREATED", content = @Content(schema = @Schema(implementation = CreateCommentResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping
  public ResponseEntity<CreateCommentResponse> createComment(@ApiIgnore @LoginUser String memberId,
      @RequestBody @Valid CreateCommentRequest request) {
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(commentService.createComment(member, request));
  }

  @Tag(name = "Comments")
  @Operation(summary = "댓글 삭제")
  @ApiResponses({
      @ApiResponse(responseCode = "204", description = "NO_CONTENT"),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @PostMapping("/delete/{commentId}")
  public ResponseEntity<Void> deleteComment(@ApiIgnore @LoginUser String memberId,
      @PathVariable @Valid Long commentId) {
    Member member = memberService.findMemberById(memberId);
    commentService.deleteComment(member, commentId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @Tag(name = "Comments")
  @Operation(summary = "게시글 댓글 조회", description = "차단된 사용자의 댓글은 보이지 않습니다.")
  @ApiResponses({
      @ApiResponse(responseCode = "200", description = "댓글 조회 성공", content = @Content(schema = @Schema(implementation = ListAllCommentResponse.class))),
      @ApiResponse(responseCode = "404", description = "존재하지 않는 리소스 접근", content = @Content(schema = @Schema(implementation = NotFoundFailResponse.class)))})
  @GetMapping("/{postId}")
  public ResponseEntity<ListAllCommentResponse> listAllComment(
      @ApiIgnore @LoginUser String memberId, @PathVariable @Valid Long postId) {
    Member member = memberService.findMemberById(memberId);
    return ResponseEntity.ok()
        .body(commentService.listAllComment(member, postId));
  }


}
