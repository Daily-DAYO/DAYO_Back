package com.seoultech.dayo.comment.controller;


import com.seoultech.dayo.comment.controller.response.ListAllCommentResponse;
import com.seoultech.dayo.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/{postId}")
    public ResponseEntity<ListAllCommentResponse> listAllComment(@PathVariable @Valid Long postId) {
        return ResponseEntity.ok()
                .body(commentService.listAllComment(postId));
    }

}
