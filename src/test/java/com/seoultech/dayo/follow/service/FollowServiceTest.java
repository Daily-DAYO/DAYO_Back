package com.seoultech.dayo.follow.service;

import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowRequest;
import com.seoultech.dayo.follow.controller.dto.response.CreateFollowResponse;
import com.seoultech.dayo.follow.repository.FollowRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FollowServiceTest {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private FollowService followService;

    @Test
    void 팔로우_생성() {
//        final Member member1 = new Member("조재영", "jdyj@naver.com", "");
//        final Member member2 = new Member("테스트", "test@naver.com", "");
//
//        given(memberRepository.save(member1)).willReturn(member1);
//        given(memberRepository.save(member2)).willReturn(member2);
//        given(memberRepository.findById(member1.getId())).willReturn(Optional.of(member1));
//        given(memberRepository.findById(member2.getId())).willReturn(Optional.of(member2));
//        final Follow follow = new Follow(new Follow.Key(member1.getId(), member2.getId()), member1, member2);
//        CreateFollowRequest request = new CreateFollowRequest(member1.getId(), member2.getId());
//        when(request.toEntity(member1, member2)).thenReturn(follow);
//        CreateFollowResponse response = new CreateFollowResponse(member1.getId(), member2.getId());
//
//        CreateFollowResponse followResponse = followService.createFollow(request);
//        assertThat(followResponse).isEqualTo(response);



    }

}