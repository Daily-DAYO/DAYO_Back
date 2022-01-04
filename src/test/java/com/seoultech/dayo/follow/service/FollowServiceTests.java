package com.seoultech.dayo.follow.service;

import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.follow.controller.dto.FollowingDto;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowRequest;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowUpRequest;
import com.seoultech.dayo.follow.controller.dto.response.CreateFollowResponse;
import com.seoultech.dayo.follow.controller.dto.response.CreateFollowUpResponse;
import com.seoultech.dayo.follow.controller.dto.response.ListAllFollowerResponse;
import com.seoultech.dayo.follow.controller.dto.response.ListAllFollowingResponse;
import com.seoultech.dayo.follow.repository.FollowRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class FollowServiceTests {

    @Mock
    private MemberRepository memberRepository;

    @Mock
    private FollowRepository followRepository;

    @InjectMocks
    private FollowService followService;

    private Member member1;
    private Member member2;

    @BeforeEach
    public void init() {
        member1 = new Member("조재영", "jdyj@naver.com", "");
        member2 = new Member("테스트", "test@naver.com", "");
        member1.setNickname("닉네임1");
        member2.setNickname("닉네임2");
    }

    @Test
    @DisplayName("팔로우 생성")
    void createFollow() {

        // given
        given(memberRepository.findById(member1.getId())).willReturn(Optional.of(member1));
        given(memberRepository.findById(member2.getId())).willReturn(Optional.of(member2));

        //when
        CreateFollowRequest request = new CreateFollowRequest(member1.getId(), member2.getId());
        Follow follow = new Follow(new Follow.Key(member1.getId(), member2.getId()), member1, member2, false);
        given(followRepository.save(any())).willReturn(follow);

        //then
        CreateFollowResponse response = followService.createFollow(request);
        assertThat(response.getMemberId()).isEqualTo(member1.getId());
        assertThat(response.getFollowerId()).isEqualTo(member2.getId());
        assertThat(response.getIsAccept()).isEqualTo(false);

    }

    @Test
    @DisplayName("맞팔로우 생성")
    void createFollowUp() {

        //given
        given(memberRepository.findById(member1.getId())).willReturn(Optional.of(member1));
        given(memberRepository.findById(member2.getId())).willReturn(Optional.of(member2));
        Follow follow1 = new Follow(new Follow.Key(member1.getId(), member2.getId()), member1, member2, false);
        given(followRepository.findFollowByMemberAndFollower(any(), any())).willReturn(Optional.of(follow1));
        
        //when
        CreateFollowUpRequest request = new CreateFollowUpRequest(member2.getId(), member1.getId());
        Follow follow2 = new Follow(new Follow.Key(member2.getId(), member1.getId()), member2, member1, true);
        given(followRepository.save(any())).willReturn(follow2);

        //then
        CreateFollowUpResponse response = followService.createFollowUp(request);
        assertThat(response.getIsAccept()).isEqualTo(true);
        assertThat(follow1.getIsAccept()).isEqualTo(true);
    }

    @Test
    @DisplayName("팔로워 조회")
    void listAllFollowers() {

        //given
        given(memberRepository.findById(member2.getId())).willReturn(Optional.of(member2));
        List<Follow> follows = new ArrayList<>();
        Follow follow1 = new Follow(new Follow.Key(member1.getId(), member2.getId()), member1, member2, false);
        follows.add(follow1);
        given(followRepository.findFollowsByFollower(any())).willReturn(follows);

        //when
        ListAllFollowerResponse response = followService.listAllFollowers(member2.getId());

        //then
        assertThat(response.getCount()).isEqualTo(1);
        assertThat(response.getData().get(0).getMemberId()).isEqualTo(member1.getId());

    }

    @Test
    @DisplayName("팔로잉 조회")
    void listAllFollowings() {

        //given
        given(memberRepository.findById(member1.getId())).willReturn(Optional.of(member1));
        List<Follow> follows = new ArrayList<>();
        Follow follow1 = new Follow(new Follow.Key(member1.getId(), member2.getId()), member1, member2, false);
        follows.add(follow1);
        given(followRepository.findFollowsByMember(any())).willReturn(follows);

        //when
        ListAllFollowingResponse response = followService.listAllFollowings(member1.getId());

        //then
        assertThat(response.getCount()).isEqualTo(1);
        assertThat(response.getData().get(0).getMemberId()).isEqualTo(member2.getId());

    }

}