package com.seoultech.dayo.post.service;


import com.seoultech.dayo.Image.Image;
import com.seoultech.dayo.Image.service.ImageService;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.folder.repository.FolderRepository;
import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.service.HashtagService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.controller.dto.PostDto;
import com.seoultech.dayo.post.controller.dto.request.CreatePostRequest;
import com.seoultech.dayo.post.controller.dto.response.CreatePostResponse;
import com.seoultech.dayo.post.controller.dto.response.ListCategoryPostResponse;
import com.seoultech.dayo.post.repository.PostRepository;
import com.seoultech.dayo.post.controller.dto.response.ListAllPostResponse;
import com.seoultech.dayo.postHashtag.PostHashtag;
import com.seoultech.dayo.postHashtag.service.PostHashtagService;
import com.seoultech.dayo.exception.NotExistFolderException;
import com.seoultech.dayo.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FolderRepository folderRepository;
    private final MemberRepository memberRepository;
    private final PostHashtagService postHashtagService;
    private final HashtagService hashtagService;
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

    public CreatePostResponse createPost(MultipartHttpServletRequest servletRequest) throws IOException {

        List<MultipartFile> files = servletRequest.getFiles("files");
        List<Image> images = imageService.storeFiles(files);
        CreatePostRequest request = createDto(servletRequest);

        Optional<Member> memberOptional = memberRepository.findById(request.getMemberId());
        Member member = memberOptional.orElseThrow(NotExistMemberException::new);

        Optional<Folder> folderOptional = folderRepository.findById(request.getFolderId());
        Folder folder = folderOptional.orElseThrow(NotExistFolderException::new);

        List<Hashtag> hashtags = hashtagService.createHashtag(request.getTags());
        Post savedPost = postRepository.save(request.toEntity(folder, member, images));

        List<PostHashtag> collect = hashtags.stream()
                .map(hashtag -> new PostHashtag(savedPost, hashtag))
                .collect(toList());

        postHashtagService.saveAll(collect);

        return CreatePostResponse.from(savedPost);
    }

    private CreatePostRequest createDto(MultipartHttpServletRequest servletRequest) {
        String contents = servletRequest.getParameter("contents");
        String memberId = servletRequest.getParameter("memberId");
        String folderId = servletRequest.getParameter("folderId");
        String privacy = servletRequest.getParameter("privacy");
        String category = servletRequest.getParameter("category");
        String tags = servletRequest.getParameter("tags");
        List<String> lists = new ArrayList<>();

        return new CreatePostRequest(contents, memberId, Long.parseLong(folderId), privacy, category, lists);
    }

}
