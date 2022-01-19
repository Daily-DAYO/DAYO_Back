package com.seoultech.dayo.post.service;


import com.seoultech.dayo.exception.NotExistPostException;
import com.seoultech.dayo.folder.service.FolderService;
import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.follow.service.FollowService;
import com.seoultech.dayo.heart.service.HeartService;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.service.ImageService;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.service.HashtagService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.controller.dto.PostDto;
import com.seoultech.dayo.post.controller.dto.request.CreatePostRequest;
import com.seoultech.dayo.post.controller.dto.response.*;
import com.seoultech.dayo.post.repository.PostRepository;
import com.seoultech.dayo.postHashtag.PostHashtag;
import com.seoultech.dayo.postHashtag.service.PostHashtagService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final FolderService folderService;
    private final MemberService memberService;
    private final PostHashtagService postHashtagService;
    private final HashtagService hashtagService;
    private final HeartService heartService;
    private final FollowService followService;
    private final ImageService imageService;

//    @Cacheable(value = "all")
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

        Member member = memberService.findMemberById(memberId);
        Folder folder = folderService.findFolderById(request.getFolderId());
        Post savedPost = postRepository.save(request.toEntity(folder, member, images));

        if(request.getTags() != null) {
            List<Hashtag> hashtags = hashtagService.createHashtag(request.getTags());
            postHashtagService.createPostHashtag(savedPost, hashtags);
        }

        return CreatePostResponse.from(savedPost);
    }

    @Transactional(readOnly = true)
    public DetailPostResponse detailPost(String memberId, Long postId) {
        Post post = findPostById(postId);
        boolean isHeart = heartService.isHeart(memberId, postId);

        return DetailPostResponse.from(post, isHeart);
    }

    @Transactional(readOnly = true)
    public ListFeedResponse listFeed(String memberId) {

        Member member = memberService.findMemberById(memberId);
        List<Follow> follows = followService.findFollowings(member);
        List<Member> members = follows.stream()
                .map(Follow::getMember)
                .collect(toList());

        members.stream()
                .map(Member::getPosts)
                .collect(toList());

        return null;
    }

    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }

    public Post findPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(NotExistPostException::new);
    }

}
