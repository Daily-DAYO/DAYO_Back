package com.seoultech.dayo.postHashtag.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.repository.HashtagRepository;
import com.seoultech.dayo.image.Image;
import com.seoultech.dayo.image.repository.ImageRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.repository.PostRepository;
import com.seoultech.dayo.postHashtag.PostHashtag;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class PostHashtagRepositoryTests {

  @Autowired
  PostHashtagRepository postHashtagRepository;

  @Autowired
  PostRepository postRepository;

  @Autowired
  HashtagRepository hashtagRepository;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  ImageRepository imageRepository;

  Post post;
  Member member;
  Image image;

  @BeforeEach
  void init() {
    member = new Member("조재영", "jdyj444@naver.com");
    image = new Image("testImage", "testImage", com.seoultech.dayo.image.Category.POST);
    imageRepository.save(image);
    post = new Post(member, "테스트1", image, Category.SCHEDULER, null);

  }

  @Test
  void deleteTest() {
    Hashtag hashtag = new Hashtag("태그");
    memberRepository.save(member);
    postRepository.save(post);
    hashtagRepository.save(hashtag);

    PostHashtag postHashtag = new PostHashtag(post, hashtag);
    postHashtagRepository.save(postHashtag);

    PostHashtag postHashtag1 = postHashtagRepository.findById(postHashtag.getKey())
        .orElseThrow();
    assertThat(postHashtag1.getPost().getId()).isEqualTo(post.getId());
    postHashtagRepository.deleteAllByPost(post);

    assertThrows(IllegalStateException.class,
        () -> postHashtagRepository.findById(postHashtag1.getKey())
            .orElseThrow(IllegalStateException::new));
  }

}