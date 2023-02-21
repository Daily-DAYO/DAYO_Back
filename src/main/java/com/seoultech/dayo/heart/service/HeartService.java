package com.seoultech.dayo.heart.service;

import com.seoultech.dayo.alarm.service.AlarmService;
import com.seoultech.dayo.exception.NotExistHeartException;
import com.seoultech.dayo.heart.Heart;
import com.seoultech.dayo.heart.controller.dto.HeartPostDto;
import com.seoultech.dayo.heart.controller.dto.MyHeartPostDto;
import com.seoultech.dayo.heart.controller.dto.request.CreateHeartRequest;
import com.seoultech.dayo.heart.controller.dto.response.CreateHeartResponse;
import com.seoultech.dayo.heart.controller.dto.response.ListAllHeartPostResponse;
import com.seoultech.dayo.heart.controller.dto.response.ListAllMyHeartPostResponse;
import com.seoultech.dayo.heart.repository.HeartRepository;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.post.service.PostService;
import com.seoultech.dayo.utils.notification.Notification;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional
public class HeartService {

  private final HeartRepository heartRepository;
  private final AlarmService alarmService;
  private final Notification notification;

  public CreateHeartResponse createHeart(Member member, Post post, CreateHeartRequest request) {
    Heart heart = request.toEntity(member, post);
    Heart savedHeart = heartRepository.save(heart);

    notification.sendHeartToPostOwner(member, post);
    return CreateHeartResponse.from(savedHeart);
  }

  public void deleteHeart(Member member, Post post) {

    Heart heart = heartRepository.findHeartByMemberAndPost(member, post)
        .orElseThrow(NotExistHeartException::new);

    post.deleteHeart(heart);
    heartRepository.delete(heart);
    alarmService.deleteHeart(member, post);
  }

  @Transactional(readOnly = true)
  public ListAllHeartPostResponse listAllHeartPost(Member member, Set<String> blockList, Long end) {
    List<Heart> hearts = listHeartsByMember(member);
    List<HeartPostDto> collect = hearts.stream()
        .filter(heart -> !blockList.contains(heart.getPost().getMember().getId()))
        .skip(end)
        .limit(20)
        .map(HeartPostDto::from)
        .collect(toList());

    return ListAllHeartPostResponse.from(collect);
  }

  @Transactional(readOnly = true)
  public ListAllMyHeartPostResponse listAllMyHeartPost(Member member, Set<String> blockList,
      Long end) {
    List<Heart> hearts = listHeartsByMember(member);
    List<MyHeartPostDto> collect = hearts.stream()
        .filter(heart -> !blockList.contains(heart.getPost().getMember().getId()))
        .skip(end)
        .limit(20)
        .map(MyHeartPostDto::from)
        .collect(toList());

    return ListAllMyHeartPostResponse.from(collect);
  }

  public List<Heart> listHeartsByMember(Member member) {
    return heartRepository.findAllByMember(member);
  }

  public boolean isHeart(String memberId, Long postId) {
    return heartRepository.existsHeartByKey(new Heart.Key(memberId, postId));
  }

  public void deleteAllByMember(Member member) {
    heartRepository.deleteAllByMember(member);
  }


}
