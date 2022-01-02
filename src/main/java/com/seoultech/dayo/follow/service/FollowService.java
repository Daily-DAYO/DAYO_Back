package com.seoultech.dayo.follow.service;


import com.seoultech.dayo.exception.NotExistFollowException;
import com.seoultech.dayo.exception.NotExistFollowerException;
import com.seoultech.dayo.exception.NotExistMemberException;
import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.follow.controller.dto.FollowerDto;
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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public CreateFollowResponse createFollow(CreateFollowRequest request) {

        Optional<Member> memberOptional = memberRepository.findById(request.getMemberId());
        Member member = memberOptional.orElseThrow(NotExistMemberException::new);

        Optional<Member> followerOptional = memberRepository.findById(request.getFollowerId());
        Member follower = followerOptional.orElseThrow(NotExistFollowerException::new);

        Follow follow = request.toEntity(member, follower);
        Follow savedFollow = followRepository.save(follow);

        return CreateFollowResponse.from(savedFollow);
    }

    @Transactional
    public CreateFollowUpResponse createFollowUp(CreateFollowUpRequest request) {

        Optional<Member> memberOptional = memberRepository.findById(request.getMemberId());
        Member member = memberOptional.orElseThrow(NotExistMemberException::new);

        Optional<Member> followerOptional = memberRepository.findById(request.getFollowerId());
        Member follower = followerOptional.orElseThrow(NotExistFollowerException::new);

        Optional<Follow> followOptional = followRepository.findFollowByMemberAndFollower(follower, member);
        Follow presentFollow = followOptional.orElseThrow(NotExistFollowException::new);
        presentFollow.setIsAccept(true);

        Follow follow = request.toEntity(member, follower);
        Follow savedFollow = followRepository.save(follow);

        return CreateFollowUpResponse.from(savedFollow);
    }

    public ListAllFollowerResponse listAllFollowers(String memberId) {

        Optional<Member> memberOptional = memberRepository.findById(memberId);
        Member member = memberOptional.orElseThrow(NotExistMemberException::new);

        List<Follow> followers = followRepository.findFollowsByFollower(member);
        List<FollowerDto> collect = followers.stream()
                .map(FollowerDto::from)
                .collect(toList());

        return ListAllFollowerResponse.from(collect);
    }

    public ListAllFollowingResponse listAllFollowings(String memberId) {

        Optional<Member> memberOptional = memberRepository.findById(memberId);
        Member member = memberOptional.orElseThrow(NotExistMemberException::new);

        List<Follow> followings = followRepository.findFollowsByMember(member);
        List<FollowingDto> collect = followings.stream()
                .map(FollowingDto::from)
                .collect(toList());

        return ListAllFollowingResponse.from(collect);
    }

}
