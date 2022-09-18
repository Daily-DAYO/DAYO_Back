package com.seoultech.dayo.comment.controller;


import com.seoultech.dayo.comment.controller.dto.request.CreateCommentRequest;
import com.seoultech.dayo.comment.controller.dto.response.CreateCommentResponse;
import com.seoultech.dayo.comment.controller.dto.response.ListAllCommentResponse;
import com.seoultech.dayo.comment.service.CommentService;
import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.exception.NotExistMemberException;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

  private final CommentService commentService;
  private final MemberService memberService;

  @PostMapping
  public ResponseEntity<CreateCommentResponse> createComment(@ApiIgnore @LoginUser String memberId,
      @RequestBody @Valid CreateCommentRequest request) {
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.status(HttpStatus.CREATED)
        .body(commentService.createComment(member, request));
  }

  @PostMapping("/delete/{commentId}")
  public ResponseEntity<Void> deleteComment(@PathVariable @Valid Long commentId) {
    commentService.deleteComment(commentId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

  @GetMapping("/{postId}")
  public ResponseEntity<ListAllCommentResponse> listAllComment(@PathVariable @Valid Long postId) {
    return ResponseEntity.ok()
        .body(commentService.listAllComment(postId));
  }


}
