package com.seoultech.dayo.domain.post.service;


import com.seoultech.dayo.domain.Image.Image;
import com.seoultech.dayo.domain.Image.service.ImageService;
import com.seoultech.dayo.domain.post.Category;
import com.seoultech.dayo.domain.post.Post;
import com.seoultech.dayo.domain.post.controller.dto.PostDto;
import com.seoultech.dayo.domain.post.controller.dto.request.CreatePostRequest;
import com.seoultech.dayo.domain.post.controller.dto.response.CreatePostResponse;
import com.seoultech.dayo.domain.post.controller.dto.response.ListCategoryPostResponse;
import com.seoultech.dayo.domain.post.repository.PostRepository;
import com.seoultech.dayo.domain.post.controller.dto.response.ListAllPostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final ImageService imageService;

    @Cacheable(value = "all")
    public ListAllPostResponse listPostAll() {

        List<Post> postList = postRepository.findAllByUsingJoin();
        List<PostDto> collect = postList.stream()
                .map(PostDto::from)
                .collect(toList());

        return new ListAllPostResponse(postList.size(), collect);
    }

    public ListCategoryPostResponse listPostByCategory(String category) {

        List<Post> postList = postRepository.findAllByCategoryUsingJoin(Category.valueOf(category));
        List<PostDto> collect = postList.stream()
                .map(PostDto::from)
                .collect(toList());

        return new ListCategoryPostResponse(postList.size(), collect);
    }

    public CreatePostResponse createPost(CreatePostRequest request, MultipartHttpServletRequest servletRequest) throws IOException {

        List<MultipartFile> files = servletRequest.getFiles("files");
        List<Image> images = imageService.storeFiles(files);



        return null;
    }

}
