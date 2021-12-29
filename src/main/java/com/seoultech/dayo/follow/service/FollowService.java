package com.seoultech.dayo.follow.service;


import com.seoultech.dayo.exception.NotExistFollowerException;
import com.seoultech.dayo.exception.NotExistMemberException;
import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowRequest;
import com.seoultech.dayo.follow.controller.dto.response.CreateFollowResponse;
import com.seoultech.dayo.follow.repository.FollowRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public CreateFollowResponse createFollow(CreateFollowRequest request) {

        Optional<Member> memberOptional = memberRepository.findById(request.getMemberId());
        Member member = memberOptional.orElseThrow(NotExistMemberException::new);

        Optional<Member> followerOptional = memberRepository.findById(request.getFollowerId());
        Member follower = followerOptional.orElseThrow(NotExistFollowerException::new);

        Follow follow = request.toEntity(member, follower);
        Follow savedFollow = followRepository.save(follow);

        return CreateFollowResponse.from(savedFollow);

    }

}
