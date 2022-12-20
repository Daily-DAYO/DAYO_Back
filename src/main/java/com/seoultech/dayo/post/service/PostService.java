package com.seoultech.dayo.post.service;


import com.seoultech.dayo.alarm.service.AlarmService;
import com.seoultech.dayo.bookmark.service.BookmarkService;
import com.seoultech.dayo.exception.InvalidPostAccess;
import com.seoultech.dayo.exception.NotExistPostException;
import com.seoultech.dayo.folder.Privacy;
import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.follow.service.FollowService;
import com.seoultech.dayo.heart.service.HeartService;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.service.ImageService;
import com.seoultech.dayo.folder.Folder;
import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.service.HashtagService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.controller.dto.DayoPick;
import com.seoultech.dayo.post.controller.dto.FeedDto;
import com.seoultech.dayo.post.controller.dto.PostDto;
import com.seoultech.dayo.post.controller.dto.request.CreatePostRequest;
import com.seoultech.dayo.post.controller.dto.request.EditPostRequest;
import com.seoultech.dayo.post.controller.dto.response.*;
import com.seoultech.dayo.post.repository.DayoPickRepository;
import com.seoultech.dayo.post.repository.PostRepository;
import com.seoultech.dayo.postHashtag.service.PostHashtagService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
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
  private final AlarmService alarmService;

  @Transactional(readOnly = true)
  public DayoPickPostListResponse dayoPickListWithCategory(Member member, String category) {

    List<Post> postList = getDayoPickWithCategory(category);

    Set<Long> likePost = getLikePost(member);

    List<DayoPick> collect = new ArrayList<>();
    Set<String> blockList = getBlockList(member);

    for (Post post : postList) {
      if (blockList.contains(post.getMember().getId())) {
        continue;
      }
      boolean isLike = likePost.contains(post.getId());
      if (isLike) {
        collect.add(DayoPick.from(post, true));
      } else {
        collect.add(DayoPick.from(post, false));
      }
    }

    collect.sort((a1, a2) -> a2.getHeartCount() - a1.getHeartCount());

    return DayoPickPostListResponse.from(collect);

  }

  @Cacheable(value = "dayoPick", key = "#category")
  public List<Post> getDayoPickWithCategory(String category) {
    return postRepository.findAllByCategoryUsingJoinMember(
        Category.valueOf(category));
  }

  @Transactional(readOnly = true)
  public ListAllPostResponse listPostAll(Member member) {

    List<Post> postList = postRepository.findAllByUsingJoinMemberOrderByCreateDate();

    Set<Long> likePost = getLikePost(member);
    Set<Long> bookmarkPost = getBookmarkPost(member);
    Set<String> blockList = getBlockList(member);

    List<PostDto> collect = new ArrayList<>();

    for (Post post : postList) {
      if (blockList.contains(post.getMember().getId())) {
        continue;
      }

      boolean isLike = likePost.contains(post.getId());
      boolean isBookmark = bookmarkPost.contains(post.getId());

      if (isLike && isBookmark) {
        collect.add(PostDto.from(post, true, true));
      } else if (isLike) {
        collect.add(PostDto.from(post, true, false));
      } else if (isBookmark) {
        collect.add(PostDto.from(post, false, true));
      } else {
        collect.add(PostDto.from(post, false, false));
      }

    }

    collect.sort((a1, a2) -> a2.getCreateDate().compareTo(a1.getCreateDate()));

    return new ListAllPostResponse(postList.size(), collect);
  }

  @Transactional(readOnly = true)
  public ListCategoryPostResponse listPostByCategory(Member member, String category) {

    List<Post> postList = postRepository.findAllByCategoryUsingJoinOrderByCreateDate(
        Category.valueOf(category));
    Set<Long> likePost = getLikePost(member);

    Set<Long> bookmarkPost = getBookmarkPost(member);
    Set<String> blockList = getBlockList(member);

    List<PostDto> collect = new ArrayList<>();

    for (Post post : postList) {

      if (blockList.contains(post.getMember().getId())) {
        continue;
      }

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
    imageService.resizeFile(images.get(0).getStoreFileName(), 220, 220);

    request.setCategory(request.getCategory());

    Post savedPost = postRepository.save(request.toEntity(member, images));
    savedPost.addFolder(folder);

    if (request.getTags() != null) {
      List<Hashtag> hashtags = hashtagService.createHashtag(request.getTags());
      postHashtagService.createPostHashtag(savedPost, hashtags);
    }

    return CreatePostResponse.from(savedPost);
  }

  public EditPostResponse editPost(EditPostRequest request, Member member, Folder folder,
      Long postId) {

    Post post = findPostById(postId);

    if (!post.getMember().getId().equals(member.getId())) {
      throw new InvalidPostAccess();
    }
    if (request.getCategory() != null) {
      post.setCategory(Category.valueOf(request.getCategory()));
    }
    if (request.getHashtags().size() > 0) {
      List<Hashtag> hashtags = hashtagService.createHashtag(request.getHashtags());
      post.deletePostHashTag();
      postHashtagService.deletePostHashtag(post);
      postHashtagService.createPostHashtag(post, hashtags);
    }
    if (request.getHashtags().size() == 0) {
      post.deletePostHashTag();
      postHashtagService.deletePostHashtag(post);
    }
    if (request.getContents() != null) {
      post.setContents(request.getContents());
    }
    if (folder != null) {
      post.addFolder(folder);
    }

    return EditPostResponse.from(post);
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
        .sorted((post1, post2) -> post2.getCreatedDate().compareTo(post1.getCreatedDate()))
        .collect(toList());

    Set<String> blockList = getBlockList(member);

    List<FeedDto> feedDtos = new ArrayList<>();
    for (Post post : postCollect) {
      if (blockList.contains(post.getMember().getId())) {
        continue;
      }
      boolean isHeart = heartService.isHeart(member.getId(), post.getId());
      boolean isBookmark = bookmarkService.isBookmark(member.getId(), post.getId());

      feedDtos.add(FeedDto.from(post, isHeart, isBookmark));
    }

    return ListFeedResponse.from(feedDtos);
  }

  public void deletePost(String memberId, Long postId) {

    Post post = findPostById(postId);
    if (!post.getMember().getId().equals(memberId)) {
      throw new InvalidPostAccess();
    }

    alarmService.deleteByPost(post);
    postRepository.deleteById(postId);
  }

  public Post findPostById(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(NotExistPostException::new);
  }

  @Transactional(readOnly = true)
  public DayoPickPostListResponse dayoPickList(Member member) {

    List<Post> postList = getDayoPickAll();

    postList.sort(Comparator.comparingInt(Post::getHeartCount));

    Set<Long> likePost = getLikePost(member);
    Set<String> blockList = getBlockList(member);

    List<DayoPick> collect = new ArrayList<>();

    for (Post post : postList) {
      if (blockList.contains(post.getMember().getId())) {
        continue;
      }
      boolean like = likePost.contains(post.getId());
      if (like) {
        collect.add(DayoPick.from(post, true));
      } else {
        collect.add(DayoPick.from(post, false));
      }
    }

    collect.sort((a1, a2) -> a2.getHeartCount() - a1.getHeartCount());

    return DayoPickPostListResponse.from(collect);
  }

  @Cacheable(value = "dayoPick", key = "1")
  public List<Post> getDayoPickAll() {
    return postRepository.findAllUsingJoinMember();
  }

  public void deleteAllByMember(Member member) {
    postRepository.deleteAllByMember(member);
  }

  private Set<Long> getLikePost(Member member) {
    return heartService.listHeartsByMember(member).stream()
        .map(heart -> heart.getPost().getId())
        .collect(toSet());
  }

  private Set<Long> getBookmarkPost(Member member) {
    return bookmarkService.listBookmarksByMember(member).stream()
        .map(bookmark -> bookmark.getPost().getId())
        .collect(toSet());
  }


  public Set<String> getBlockList(Member member) {
    Set<String> blockList = member.getBlockList().stream().map(block -> block.getTarget().getId())
        .collect(toSet());
    return blockList;
  }
}
