package com.seoultech.dayo.domain.post.service;


import com.seoultech.dayo.domain.comment.Comment;
import com.seoultech.dayo.domain.comment.CommentRepository;
import com.seoultech.dayo.domain.heart.Heart;
import com.seoultech.dayo.domain.heart.HeartRepository;
import com.seoultech.dayo.domain.post.Post;
import com.seoultech.dayo.domain.post.controller.dto.PostDto;
import com.seoultech.dayo.domain.post.controller.dto.request.CreatePostRequest;
import com.seoultech.dayo.domain.post.controller.dto.response.CreatePostResponse;
import com.seoultech.dayo.domain.post.repository.PostRepository;
import com.seoultech.dayo.domain.post.controller.dto.response.ListPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

//    @Cacheable
    public ListPostResponse listPostAll() {

        List<Post> postList = postRepository.findAll();
        List<PostDto> collect = postList.stream()
                .map(PostDto::from)
                .collect(toList());

        return new ListPostResponse(postList.size(), collect);
    }

    public CreatePostResponse createPost(CreatePostRequest request, MultipartHttpServletRequest servletRequest) {



    }

}
