package com.seoultech.dayo.comment.controller;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.seoultech.dayo.comment.controller.dto.request.CreateCommentRequest;
import com.seoultech.dayo.comment.controller.dto.response.CreateCommentResponse;
import com.seoultech.dayo.comment.controller.dto.response.ListAllCommentResponse;
import com.seoultech.dayo.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

  private final CommentService commentService;

  @PostMapping
  public ResponseEntity<CreateCommentResponse> createComment(HttpServletRequest servletRequest,
      @RequestBody @Valid CreateCommentRequest request) throws FirebaseMessagingException {
    String memberId = servletRequest.getAttribute("memberId").toString();
    return ResponseEntity.status(HttpStatus.CREATED)
        .body(commentService.createComment(memberId, request));
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
