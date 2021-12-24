package com.seoultech.dayo.post.controller;


import com.seoultech.dayo.post.controller.dto.request.CreatePostRequest;
import com.seoultech.dayo.post.controller.dto.response.CreatePostResponse;
import com.seoultech.dayo.post.controller.dto.response.ListAllPostResponse;
import com.seoultech.dayo.post.controller.dto.response.ListCategoryPostResponse;
import com.seoultech.dayo.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.validation.Valid;
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

    @GetMapping("/{category}")
    public ResponseEntity<ListCategoryPostResponse> listPostByCategory(@PathVariable String category) {
        return ResponseEntity.ok()
                .body(postService.listPostByCategory(category));
    }

    @PostMapping
    public ResponseEntity<CreatePostResponse> createPost(@ModelAttribute @Valid CreatePostRequest request) throws IOException {

        return ResponseEntity.ok()
                .body(postService.createPost(request));
    }


}
