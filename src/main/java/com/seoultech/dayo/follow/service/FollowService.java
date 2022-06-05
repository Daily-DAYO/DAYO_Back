package com.seoultech.dayo.follow.service;


import com.google.firebase.messaging.FirebaseMessagingException;
import com.seoultech.dayo.alarm.Topic;
import com.seoultech.dayo.alarm.service.AlarmService;
import com.seoultech.dayo.exception.NotExistFollowException;
import com.seoultech.dayo.config.fcm.FcmMessageService;
import com.seoultech.dayo.config.fcm.Note;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
@Transactional
public class FollowService {

  private final FollowRepository followRepository;
  private final FcmMessageService fcmMessageService;
  private final AlarmService alarmService;

  public CreateFollowResponse createFollow(Member member, Member follower,
      CreateFollowRequest request) throws FirebaseMessagingException {

    Follow follow = request.toEntity(member, follower);
    Follow savedFollow = followRepository.save(follow);

    Map<String, String> data = new HashMap<>();
    data.put("body", member.getNickname() + "님이 회원님을 팔로우해요.");
    Note note = new Note(
        "DAYO",
        null,
        data,
        null
    );

    alarmService.saveAlarmFollow(note, follower, follower.getNickname(), Topic.FOLLOW);

    // TODO: refactoring
    if (follower.getDeviceToken() != null) {
      fcmMessageService.sendMessage(note, follower.getDeviceToken(), Topic.FOLLOW);
    }

    return CreateFollowResponse.from(savedFollow);
  }

  public CreateFollowUpResponse createFollowUp(Member member, Member follower,
      CreateFollowUpRequest request) throws FirebaseMessagingException {

    Optional<Follow> followOptional = followRepository.findFollowByMemberAndFollower(follower,
        member);
    Follow presentFollow = followOptional.orElseThrow(NotExistFollowException::new);
    presentFollow.setIsAccept(true);

    Follow follow = request.toEntity(member, follower);
    Follow savedFollow = followRepository.save(follow);

    Map<String, String> data = new HashMap<>();
    data.put("body", member.getNickname() + "님이 회원님을 팔로우해요.");
    Note note = new Note(
        "DAYO",
        null,
        data,
        null
    );

    alarmService.saveAlarmFollow(note, follower, follower.getNickname(), Topic.FOLLOW);

    // TODO: refactoring
    if (follower.getDeviceToken() != null) {
      fcmMessageService.sendMessage(note, follower.getDeviceToken(), Topic.FOLLOW);
    }

    return CreateFollowUpResponse.from(savedFollow);
  }

  @Transactional(readOnly = true)
  public ListAllFollowerResponse listAllFollowers(Member me, Member member) {

    List<Follow> followers = followRepository.findFollowsByFollower(member);
    List<Follow> myFollowings = followRepository.findFollowsByMember(me);
    Set<String> myCollect = myFollowings.stream()
        .map(follow -> follow.getFollower().getId())
        .collect(toSet());

    List<FollowerDto> collect = new ArrayList<>();

    for (Follow follower : followers) {
      if (myCollect.contains(follower.getMember().getId())) {
        collect.add(FollowerDto.from(follower, true));
      } else {
        collect.add(FollowerDto.from(follower, false));
      }
    }

    return ListAllFollowerResponse.from(collect);
  }

  @Transactional(readOnly = true)
  public ListAllFollowingResponse listAllFollowings(Member me, Member member) {

    List<Follow> followings = followRepository.findFollowsByMember(member);
    List<Follow> myFollowings = followRepository.findFollowsByMember(me);
    Set<String> myCollect = myFollowings.stream()
        .map(follow -> follow.getFollower().getId())
        .collect(toSet());

    List<FollowingDto> collect = new ArrayList<>();

    for (Follow following : followings) {
      if (myCollect.contains(following.getFollower().getId())) {
        collect.add(FollowingDto.from(following, true));
      } else {
        collect.add(FollowingDto.from(following, false));
      }
    }

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

  public void deleteAllByMember(Member member) {
    followRepository.deleteAllByMember(member);
    followRepository.deleteAllByFollower(member);
  }

}
