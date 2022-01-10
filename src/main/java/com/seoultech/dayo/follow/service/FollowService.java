package com.seoultech.dayo.follow.service;


import com.seoultech.dayo.exception.NotExistFollowException;
import com.seoultech.dayo.exception.NotExistFollowerException;
import com.seoultech.dayo.exception.NotExistMemberException;
import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.follow.controller.dto.FollowerDto;
import com.seoultech.dayo.follow.controller.dto.FollowingDto;
import com.seoultech.dayo.follow.controller.dto.MyFollowerDto;
import com.seoultech.dayo.follow.controller.dto.MyFollowingDto;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowRequest;
import com.seoultech.dayo.follow.controller.dto.request.CreateFollowUpRequest;
import com.seoultech.dayo.follow.controller.dto.response.*;
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
@Transactional
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    public CreateFollowResponse createFollow(String memberId, CreateFollowRequest request) {

        Member member = findMember(memberId);
        Member follower = findFollower(request.getFollowerId());

        Follow follow = request.toEntity(member, follower);
        Follow savedFollow = followRepository.save(follow);

        return CreateFollowResponse.from(savedFollow);
    }

    public CreateFollowUpResponse createFollowUp(String memberId, CreateFollowUpRequest request) {

        Member member = findMember(memberId);
        Member follower = findFollower(request.getFollowerId());

        Optional<Follow> followOptional = followRepository.findFollowByMemberAndFollower(follower, member);
        Follow presentFollow = followOptional.orElseThrow(NotExistFollowException::new);
        presentFollow.setIsAccept(true);

        Follow follow = request.toEntity(member, follower);
        Follow savedFollow = followRepository.save(follow);

        return CreateFollowUpResponse.from(savedFollow);
    }

    @Transactional(readOnly = true)
    public ListAllFollowerResponse listAllFollowers(String memberId) {

        Member member = findMember(memberId);

        List<Follow> followers = followRepository.findFollowsByFollower(member);
        List<FollowerDto> collect = followers.stream()
                .map(FollowerDto::from)
                .collect(toList());

        return ListAllFollowerResponse.from(collect);
    }

    @Transactional(readOnly = true)
    public ListAllFollowingResponse listAllFollowings(String memberId) {

        Member member = findMember(memberId);

        List<Follow> followings = followRepository.findFollowsByMember(member);
        List<FollowingDto> collect = followings.stream()
                .map(FollowingDto::from)
                .collect(toList());

        return ListAllFollowingResponse.from(collect);
    }

    @Transactional(readOnly = true)
    public ListAllMyFollowerResponse listAllMyFollowers(String memberId) {

        Member member = findMember(memberId);

        List<Follow> followers = followRepository.findFollowsByFollower(member);
        List<MyFollowerDto> collect = followers.stream()
                .map(MyFollowerDto::from)
                .collect(toList());

        return ListAllMyFollowerResponse.from(collect);
    }

    @Transactional(readOnly = true)
    public ListAllMyFollowingResponse listAllMyFollowings(String memberId) {

        Member member = findMember(memberId);

        List<Follow> followings = followRepository.findFollowsByMember(member);
        List<MyFollowingDto> collect = followings.stream()
                .map(MyFollowingDto::from)
                .collect(toList());

        return ListAllMyFollowingResponse.from(collect);
    }

    public void deleteFollow(String memberId, String followerId) {
        followRepository.deleteById(new Follow.Key(memberId, followerId));
    }

    //TODO 리팩토링
    private Member findMember(String memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(NotExistMemberException::new);
    }

    //TODO 리팩토링
    private Member findFollower(String followerId) {
        return memberRepository.findById(followerId)
                .orElseThrow(NotExistFollowerException::new);
    }

}
