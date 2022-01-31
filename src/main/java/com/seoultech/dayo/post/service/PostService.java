package com.seoultech.dayo.post.service;


import com.seoultech.dayo.exception.NotExistPostException;
import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.follow.service.FollowService;
import com.seoultech.dayo.heart.Heart;
import com.seoultech.dayo.heart.service.HeartService;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.service.ImageService;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.service.HashtagService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.controller.dto.PostDto;
import com.seoultech.dayo.post.controller.dto.request.CreatePostRequest;
import com.seoultech.dayo.post.controller.dto.response.*;
import com.seoultech.dayo.post.repository.PostRepository;
import com.seoultech.dayo.postHashtag.service.PostHashtagService;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

  private final PostRepository postRepository;
  private final PostHashtagService postHashtagService;
  private final HashtagService hashtagService;
  private final HeartService heartService;
  private final FollowService followService;
  private final ImageService imageService;

  //    @Cacheable(value = "all")
  @Transactional(readOnly = true)
  public ListAllPostResponse listPostAll(Member member) {

    List<Post> postList = postRepository.findAllByUsingJoinMemberOrderByCreateDate();
    List<Heart> hearts = heartService.listHeartsByMember(member);
    Set<Long> likePost = hearts.stream()
        .map(heart -> heart.getPost().getId())
        .collect(toSet());

    List<PostDto> collect = new ArrayList<>();

    for (Post post : postList) {
      if (likePost.contains(post.getId())) {
        collect.add(PostDto.from(post, true));
      } else {
        collect.add(PostDto.from(post, false));
      }
    }

    return new ListAllPostResponse(postList.size(), collect);
  }

  @Transactional(readOnly = true)
  public ListCategoryPostResponse listPostByCategory(Member member, String category) {

    List<Post> postList = postRepository.findAllByCategoryUsingJoinOrderByCreateDate(
        Category.valueOf(category));
    Set<Long> likePost = heartService.listHeartsByMember(member).stream()
        .map(heart -> heart.getPost().getId())
        .collect(toSet());

    /**
     * 이득(혜택)
     * 윗 스타일 : 초기화 코드가 없다, 불변형태(add, remove 등이 없다)
     * 아랫 스타일 : 진입장벽이 낮다.(기본 문법만 알면 사용할 수 있다.), 가변형태
     *
     * 성능 얘기 : 문맥 파악을 하고 성능 얘기를 해야함.
     */
    List<PostDto> collect = postList.stream()
        .map(post -> {
          if (likePost.contains(post.getId())) {
            return PostDto.from(post, true);
          } else {
            return PostDto.from(post, false);
          }
        })
        .collect(toList());

//    for (Post post : postList) {
//      if (likePost.contains(post.getId())) {
//        collect.add(PostDto.from(post, true));
//      } else {
//        collect.add(PostDto.from(post, false));
//      }
//    }

    return new ListCategoryPostResponse(postList.size(), collect);
  }

  public CreatePostResponse createPost(Member member, Folder folder, CreatePostRequest request)
      throws IOException {

    List<MultipartFile> files = request.getFiles();
    List<Image> images = imageService.storeFiles(files);

    Post savedPost = postRepository.save(request.toEntity(member, images));
    savedPost.addFolder(folder);
    
    if (request.getTags() != null) {
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
  public ListFeedResponse listFeed(Member member) {

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
