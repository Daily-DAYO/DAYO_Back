package com.seoultech.dayo.post.service;


import com.seoultech.dayo.exception.NotExistPostException;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.service.ImageService;
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
import com.seoultech.dayo.post.controller.dto.response.DetailPostResponse;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final FolderRepository folderRepository;
    private final MemberRepository memberRepository;
    private final PostHashtagService postHashtagService;
    private final HashtagService hashtagService;
    private final ImageService imageService;

    @Cacheable(value = "all")
    @Transactional(readOnly = true)
    public ListAllPostResponse listPostAll() {

        List<Post> postList = postRepository.findAllByUsingJoin();
        List<PostDto> collect = postList.stream()
                .map(PostDto::from)
                .collect(toList());

        return new ListAllPostResponse(postList.size(), collect);
    }

    @Transactional(readOnly = true)
    public ListCategoryPostResponse listPostByCategory(String category) {

        List<Post> postList = postRepository.findAllByCategoryUsingJoin(Category.valueOf(category));
        List<PostDto> collect = postList.stream()
                .map(PostDto::from)
                .collect(toList());

        return new ListCategoryPostResponse(postList.size(), collect);
    }

    public CreatePostResponse createPost(String memberId, CreatePostRequest request) throws IOException {

        List<MultipartFile> files = request.getFiles();
        List<Image> images = imageService.storeFiles(files);

        Member member = findMember(memberId);
        Folder folder = findFolder(request.getFolderId());

        List<Hashtag> hashtags = hashtagService.createHashtag(request.getTags());
        Post savedPost = postRepository.save(request.toEntity(folder, member, images));

        List<PostHashtag> collect = hashtags.stream()
                .map(hashtag -> new PostHashtag(savedPost, hashtag))
                .collect(toList());

        postHashtagService.saveAll(collect);

        return CreatePostResponse.from(savedPost);
    }

    @Transactional(readOnly = true)
    public DetailPostResponse detailPost(Long postId) {

        Post post = findPost(postId);

        return DetailPostResponse.from(post, post.getMember());
    }

    public void deletePost(Long postId) {
        postRepository.findById(postId);
    }

    private Post findPost(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(NotExistPostException::new);
    }

    private Folder findFolder(Long folderId) {
        return folderRepository.findById(folderId)
                .orElseThrow(NotExistFolderException::new);
    }

    private Member findMember(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(NotExistMemberException::new);
    }
}
