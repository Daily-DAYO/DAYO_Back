package com.seoultech.dayo.comment.controller;


import com.seoultech.dayo.comment.controller.request.CreateCommentRequest;
import com.seoultech.dayo.comment.controller.response.CreateCommentResponse;
import com.seoultech.dayo.comment.controller.response.ListAllCommentResponse;
import com.seoultech.dayo.comment.service.CommentService;
import com.seoultech.dayo.config.jwt.TokenProvider;
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
    private final TokenProvider tokenProvider;

    @PostMapping
    public ResponseEntity<CreateCommentResponse> createComment(HttpServletRequest servletRequest, @RequestBody @Valid CreateCommentRequest request) {
        String memberId = getDataInToken(servletRequest);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(commentService.createComment(memberId, request));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ListAllCommentResponse> listAllComment(@PathVariable @Valid Long postId) {
        return ResponseEntity.ok()
                .body(commentService.listAllComment(postId));
    }

    private String getDataInToken(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization");
        return tokenProvider.getDataFromToken(token);
    }

}
