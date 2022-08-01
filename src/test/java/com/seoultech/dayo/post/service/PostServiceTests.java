package com.seoultech.dayo.post.service;

import static com.seoultech.dayo.image.Category.PROFILE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.service.HashtagService;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.repository.ImageRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.controller.dto.request.EditPostRequest;
import com.seoultech.dayo.post.repository.DayoPickRepository;
import com.seoultech.dayo.post.repository.PostRepository;
import com.seoultech.dayo.postHashtag.repository.PostHashtagRepository;
import com.seoultech.dayo.postHashtag.service.PostHashtagService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class PostServiceTests {

  @Autowired
  DayoPickRepository dayoPickRepository;

  @Autowired
  PostRepository postRepository;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  PostService postService;

  @Autowired
  PostHashtagService postHashtagService;

  @Autowired
  HashtagService hashtagService;

  @Autowired
  ImageRepository imageRepository;

  Post post;
  Member member;
  Image image;

  @BeforeEach
  void init() {
    member = new Member("조재영", "jdyj@naver.com");
    member.setNickname("재영");
    image = new Image("testImage", "testImage", com.seoultech.dayo.image.Category.POST);
    imageRepository.save(image);
    post = new Post(member, "테스트1", image, Category.SCHEDULER, null);
  }

  @Test
  @Transactional
  void editTest() {
    List<String> hashtags = new ArrayList<>();
    hashtags.add("asd");
    hashtags.add("asfd");
    hashtags.add("axsd");

    List<String> requestHashtags = new ArrayList<>();
    requestHashtags.add("이건");
    requestHashtags.add("원래");
    requestHashtags.add("있던것");

    Member savedMember = memberRepository.save(member);
    EditPostRequest request = new EditPostRequest(null, hashtags, null, null);
    Post savedPost = postRepository.save(post);

    List<Hashtag> hashtagList = hashtagService.createHashtag(requestHashtags);
    postHashtagService.createPostHashtag(savedPost, hashtagList);
    postService.editPost(request, member, null, savedPost.getId());

    assertThat(post.getPostHashtags().size()).isEqualTo(3);

  }


}