package com.seoultech.dayo.domain.post.controller;


import com.seoultech.dayo.domain.Image.service.ImageService;
import com.seoultech.dayo.domain.post.controller.dto.request.CreatePostRequest;
import com.seoultech.dayo.domain.post.controller.dto.response.CreatePostResponse;
import com.seoultech.dayo.domain.post.controller.dto.response.ListAllPostResponse;
import com.seoultech.dayo.domain.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping
    public ResponseEntity<ListAllPostResponse> listAllPost() {
        return ResponseEntity.ok()
                .body(postService.listPostAll());
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request, MultipartHttpServletRequest servletRequest) throws IOException {
        return ResponseEntity.ok()
                .body(postService.createPost(request, servletRequest));
    }


}
