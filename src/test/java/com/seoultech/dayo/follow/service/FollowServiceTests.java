package com.seoultech.dayo.follow.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.seoultech.dayo.alarm.Topic;
import com.seoultech.dayo.alarm.service.AlarmService;
import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.follow.Follow.Key;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowRequest;
import com.seoultech.dayo.follow.repository.FollowRepository;
import com.seoultech.dayo.member.Member;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class FollowServiceTests {

  @Mock
  FollowRepository followRepository;

  @Mock
  AlarmService alarmService;

  @InjectMocks
  FollowService followService;

  Member member;
  Member follower;

  @BeforeEach
  void init() {
    member = new Member("조재영", "jdyj@naver.com");
    follower = new Member("홍길동", "test@naver.com");

    member.setNickname("조재영");
    follower.setNickname("홍길동");
  }

  @Test
  @DisplayName("팔로우 - 알람 보내기 테스트")
  void createFollowTest() {
    Follow follow = new Follow(new Key(member.getId(), follower.getId()), member, follower, false);

    CreateFollowRequest request = new CreateFollowRequest(follower.getId());

    given(followRepository.save(any())).willReturn(follow);
       follower.setDeviceToken("test");
    followService.createFollow(member, follower, request);

//    verify(kafkaProducer, times(1)).sendMessage(Topic.FOLLOW,
//        "{\"subject\":\"DAYO\",\"topic\":\"FOLLOW\",\"body\":\"조재영님이 회원님을 팔로우해요.\",\"content\":\"님이 회원님을 팔로우해요.\",\"deviceToken\":\"test\",\"memberId\":\""
//            + member.getId() + "\"}");
  }

  @Test
  @DisplayName("팔로우 - 알람 안보내기 테스트")
  void createFollowTest2() {
    Follow follow = new Follow(new Key(member.getId(), follower.getId()), member, follower, false);

    CreateFollowRequest request = new CreateFollowRequest(follower.getId());

    given(followRepository.save(any())).willReturn(follow);
//    doNothing().when(kafkaProducer).sendMessage(Topic.FOLLOW, "테스트 메세지");
    followService.createFollow(member, follower, request);

//    verify(kafkaProducer, times(0)).sendMessage(Topic.FOLLOW,
//        "{\"subject\":\"DAYO\",\"topic\":\"FOLLOW\",\"body\":\"조재영님이 회원님을 팔로우해요.\",\"content\":\"님이 회원님을 팔로우해요.\",\"deviceToken\":\"test\",\"memberId\":\""
//            + member.getId() + "\"}");
  }

}

