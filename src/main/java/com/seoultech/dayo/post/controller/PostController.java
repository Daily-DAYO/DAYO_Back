package com.seoultech.dayo.post.controller;


import com.seoultech.dayo.config.jwt.TokenProvider;
import com.seoultech.dayo.post.controller.dto.request.CreatePostRequest;
import com.seoultech.dayo.post.controller.dto.response.CreatePostResponse;
import com.seoultech.dayo.post.controller.dto.response.DetailPostResponse;
import com.seoultech.dayo.post.controller.dto.response.ListAllPostResponse;
import com.seoultech.dayo.post.controller.dto.response.ListCategoryPostResponse;
import com.seoultech.dayo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final TokenProvider tokenProvider;

    @GetMapping
    public ResponseEntity<ListAllPostResponse> listAllPost() {
        return ResponseEntity.ok()
                .body(postService.listPostAll());
    }

    @GetMapping("/category/{category}")
    public ResponseEntity<ListCategoryPostResponse> listPostByCategory(@PathVariable @Valid String category) {
        return ResponseEntity.ok()
                .body(postService.listPostByCategory(category));
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(HttpServletRequest servletRequest, @ModelAttribute @Valid CreatePostRequest request) throws IOException {
        String memberId = getDataInToken(servletRequest);
        return ResponseEntity.ok()
                .body(postService.createPost(memberId, request));
    }

    @PostMapping("/delete/{postId}")
    public ResponseEntity<Void> deletePost(Long postId) {
        postService.deletePost(postId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{postId}")
    public ResponseEntity<DetailPostResponse> detailPost(@PathVariable @Valid Long postId) {
        return ResponseEntity.ok()
                .body(postService.detailPost(postId));
    }

    private String getDataInToken(HttpServletRequest servletRequest) {
        String token = servletRequest.getHeader("Authorization").substring(7);
        return tokenProvider.getDataFromToken(token);
    }

}
