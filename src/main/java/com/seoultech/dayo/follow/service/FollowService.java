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

  public CreateFollowResponse createFollow(Member member, Member follower,
      CreateFollowRequest request) {

    Follow follow = request.toEntity(member, follower);
    Follow savedFollow = followRepository.save(follow);

    return CreateFollowResponse.from(savedFollow);
  }

  public CreateFollowUpResponse createFollowUp(Member member, Member follower,
      CreateFollowUpRequest request) {

    Optional<Follow> followOptional = followRepository.findFollowByMemberAndFollower(follower,
        member);
    Follow presentFollow = followOptional.orElseThrow(NotExistFollowException::new);
    presentFollow.setIsAccept(true);

    Follow follow = request.toEntity(member, follower);
    Follow savedFollow = followRepository.save(follow);

    return CreateFollowUpResponse.from(savedFollow);
  }

  @Transactional(readOnly = true)
  public ListAllFollowerResponse listAllFollowers(Member member) {

    List<Follow> followers = followRepository.findFollowsByFollower(member);
    List<FollowerDto> collect = followers.stream()
        .map(FollowerDto::from)
        .collect(toList());

    return ListAllFollowerResponse.from(collect);
  }

  @Transactional(readOnly = true)
  public ListAllFollowingResponse listAllFollowings(Member member) {

    List<Follow> followings = followRepository.findFollowsByMember(member);
    List<FollowingDto> collect = followings.stream()
        .map(FollowingDto::from)
        .collect(toList());

    return ListAllFollowingResponse.from(collect);
  }

  @Transactional(readOnly = true)
  public ListAllMyFollowerResponse listAllMyFollowers(Member member) {

    List<Follow> followers = followRepository.findFollowsByFollower(member);
    List<MyFollowerDto> collect = followers.stream()
        .map(MyFollowerDto::from)
        .collect(toList());

    return ListAllMyFollowerResponse.from(collect);
  }

  @Transactional(readOnly = true)
  public ListAllMyFollowingResponse listAllMyFollowings(Member member) {

    List<Follow> followings = followRepository.findFollowsByMember(member);
    List<MyFollowingDto> collect = followings.stream()
        .map(MyFollowingDto::from)
        .collect(toList());

    return ListAllMyFollowingResponse.from(collect);
  }

  public List<Follow> findFollowings(Member member) {
    return followRepository.findFollowsByMember(member);
  }

  public void deleteFollow(String memberId, String followerId) {
    followRepository.deleteById(new Follow.Key(memberId, followerId));
  }

  public boolean isFollow(String memberId, String followerId) {
    return followRepository.existsById(new Follow.Key(memberId, followerId));
  }

}
