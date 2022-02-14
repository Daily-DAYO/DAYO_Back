package com.seoultech.dayo.post.service;


import com.seoultech.dayo.bookmark.Bookmark;
import com.seoultech.dayo.bookmark.service.BookmarkService;
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
import com.seoultech.dayo.post.Privacy;
import com.seoultech.dayo.post.controller.dto.FeedDto;
import com.seoultech.dayo.post.controller.dto.FeedDto.CommentDto;
import com.seoultech.dayo.post.controller.dto.PostDto;
import com.seoultech.dayo.post.controller.dto.request.CreatePostRequest;
import com.seoultech.dayo.post.controller.dto.response.*;
import com.seoultech.dayo.post.repository.PostRepository;
import com.seoultech.dayo.postHashtag.service.PostHashtagService;
import java.util.ArrayList;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class PostService {

  private final PostRepository postRepository;
  private final PostHashtagService postHashtagService;
  private final HashtagService hashtagService;
  private final HeartService heartService;
  private final BookmarkService bookmarkService;
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

    List<Bookmark> bookmarks = bookmarkService.listBookmarksByMember(member);
    Set<Long> bookmarkPost = bookmarks.stream()
        .map(bookmark -> bookmark.getPost().getId())
        .collect(toSet());

    List<PostDto> collect = new ArrayList<>();

    for (Post post : postList) {

      boolean like = likePost.contains(post.getId());
      boolean bookmark = bookmarkPost.contains(post.getId());

      if (like && bookmark) {
        collect.add(PostDto.from(post, true, true));
      } else if (like) {
        collect.add(PostDto.from(post, true, false));
      } else if (bookmark) {
        collect.add(PostDto.from(post, false, true));
      } else {
        collect.add(PostDto.from(post, false, false));
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

    List<Bookmark> bookmarks = bookmarkService.listBookmarksByMember(member);
    Set<Long> bookmarkPost = bookmarks.stream()
        .map(bookmark -> bookmark.getPost().getId())
        .collect(toSet());

    List<PostDto> collect = new ArrayList<>();

    for (Post post : postList) {

      boolean like = likePost.contains(post.getId());
      boolean bookmark = bookmarkPost.contains(post.getId());

      if (like && bookmark) {
        collect.add(PostDto.from(post, true, true));
      } else if (like) {
        collect.add(PostDto.from(post, true, false));
      } else if (bookmark) {
        collect.add(PostDto.from(post, false, true));
      } else {
        collect.add(PostDto.from(post, false, false));
      }

    }

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
    boolean isBookmark = bookmarkService.isBookmark(memberId, postId);

    return DetailPostResponse.from(post, isHeart, isBookmark);
  }

  @Transactional(readOnly = true)
  public ListFeedResponse listFeed(Member member) {

    List<Follow> follows = followService.findFollowings(member);
    List<Member> members = follows.stream()
        .map(Follow::getFollower)
        .collect(toList());

    List<Post> posts = new ArrayList<>();
    for (Member m : members) {
      posts.addAll(m.getPosts());
    }

    List<Post> postCollect = posts.stream()
        .filter(post -> post.getPrivacy() != Privacy.ONLY_ME)
        .collect(toList());

    List<FeedDto> feedDtos = new ArrayList<>();
    for (Post post : postCollect) {

      List<CommentDto> temp = post.getComments().stream()
          .map(CommentDto::from)
          .collect(toList());

      boolean isHeart = heartService.isHeart(member.getId(), post.getId());
      boolean isBookmark = bookmarkService.isBookmark(member.getId(), post.getId());

      feedDtos.add(FeedDto.from(post, isHeart, isBookmark, temp));
    }

    return ListFeedResponse.from(feedDtos);
  }

  public void deletePost(Long postId) {
    postRepository.deleteById(postId);
  }

  public Post findPostById(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(NotExistPostException::new);
  }

}
