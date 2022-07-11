package com.seoultech.dayo.heart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import com.seoultech.dayo.heart.Heart;
import com.seoultech.dayo.heart.Heart.Key;
import com.seoultech.dayo.heart.controller.dto.request.CreateHeartRequest;
import com.seoultech.dayo.heart.controller.dto.response.CreateHeartResponse;
import com.seoultech.dayo.heart.repository.HeartRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class HeartServiceTests {

  @Autowired
  HeartRepository heartRepository;

  @Autowired
  HeartService heartService;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  PostRepository postRepository;

  Member member;
  Post post;

  @BeforeEach
  void init() {
    member = new Member("조재영", "jdyj444@naver.com");
    post = new Post(member, "테스트1", "testimage", Category.SCHEDULER, null);
  }

  @Test
  @Transactional
  @Rollback
  void createHeart() {

    Member savedMember = memberRepository.save(member);
    Post savedPost = postRepository.save(post);

    CreateHeartRequest request = new CreateHeartRequest();
    heartService.createHeart(savedMember, savedPost, request);

    Post post1 = postRepository.findById(savedPost.getId()).orElseThrow();

    assertThat(post1.getHearts().size()).isEqualTo(1);

  }

  @Test
  @Transactional
  void deleteHeart() {

    Member savedMember = memberRepository.save(member);
    Post savedPost = postRepository.save(post);

    CreateHeartRequest request = new CreateHeartRequest();
    heartService.createHeart(savedMember, savedPost, request);

    heartService.deleteHeart(savedMember, savedPost);

    Post post1 = postRepository.findById(savedPost.getId()).orElseThrow();

    assertThat(post1.getHearts().size()).isEqualTo(0);

  }


}