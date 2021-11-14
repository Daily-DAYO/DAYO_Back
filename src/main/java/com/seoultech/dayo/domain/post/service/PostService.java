package com.seoultech.dayo.domain.post.service;


import com.seoultech.dayo.domain.Image.Image;
import com.seoultech.dayo.domain.Image.service.ImageService;
import com.seoultech.dayo.domain.folder.Folder;
import com.seoultech.dayo.domain.folder.repository.FolderRepository;
import com.seoultech.dayo.domain.folder.service.FolderService;
import com.seoultech.dayo.domain.hashtag.Hashtag;
import com.seoultech.dayo.domain.hashtag.service.HashtagService;
import com.seoultech.dayo.domain.member.Member;
import com.seoultech.dayo.domain.member.repository.MemberRepository;
import com.seoultech.dayo.domain.post.Category;
import com.seoultech.dayo.domain.post.Post;
import com.seoultech.dayo.domain.post.controller.dto.PostDto;
import com.seoultech.dayo.domain.post.controller.dto.request.CreatePostRequest;
import com.seoultech.dayo.domain.post.controller.dto.response.CreatePostResponse;
import com.seoultech.dayo.domain.post.controller.dto.response.ListCategoryPostResponse;
import com.seoultech.dayo.domain.post.repository.PostRepository;
import com.seoultech.dayo.domain.post.controller.dto.response.ListAllPostResponse;
import com.seoultech.dayo.domain.postHashtag.repository.PostHashtagRepository;
import com.seoultech.dayo.exception.NotExistFolderException;
import com.seoultech.dayo.exception.NotExistMemberException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final FolderRepository folderRepository;
    private final PostHashtagRepository postHashtagRepository;
    private final MemberRepository memberRepository;
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

    public CreatePostResponse createPost(CreatePostRequest request, MultipartHttpServletRequest servletRequest) throws IOException {

        List<MultipartFile> files = servletRequest.getFiles("files");
        List<Image> images = imageService.storeFiles(files);

        Optional<Member> memberOptional = memberRepository.findById(request.getMemberId());
        Member member = memberOptional.orElseThrow(NotExistMemberException::new);

        Optional<Folder> folderOptional = folderRepository.findById(request.getFolderId());
        Folder folder = folderOptional.orElseThrow(NotExistFolderException::new);

        List<Hashtag> hashtag = hashtagService.createHashtag(request.getTags());
        Post post = request.toEntity(folder, member, images);

        return null;
    }

}
