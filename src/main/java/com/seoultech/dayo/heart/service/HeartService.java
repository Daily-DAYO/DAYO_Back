package com.seoultech.dayo.heart.service;

import com.seoultech.dayo.alarm.Topic;
import com.seoultech.dayo.alarm.service.AlarmService;
import com.seoultech.dayo.config.fcm.Note;
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
import com.seoultech.dayo.utils.KafkaProducer;
import com.seoultech.dayo.utils.json.JsonData;
import java.util.HashMap;
import java.util.Map;
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
  private final KafkaProducer kafkaProducer;

  public CreateHeartResponse createHeart(Member member, Post post, CreateHeartRequest request) {
    Heart heart = request.toEntity(member, post);
    Heart savedHeart = heartRepository.save(heart);

    sendAlarmToPostOwner(member, post);
    return CreateHeartResponse.from(savedHeart);
  }

  public void deleteHeart(String memberId, Long postId) {
    heartRepository.deleteById(new Heart.Key(memberId, postId));
  }

  @Transactional(readOnly = true)
  public ListAllHeartPostResponse listAllHeartPost(Member member) {
    List<Heart> hearts = listHeartsByMember(member);
    List<HeartPostDto> collect = hearts.stream()
        .map(HeartPostDto::from)
        .collect(toList());

    return ListAllHeartPostResponse.from(collect);
  }

  @Transactional(readOnly = true)
  public ListAllMyHeartPostResponse listAllMyHeartPost(Member member) {
    List<Heart> hearts = listHeartsByMember(member);
    List<MyHeartPostDto> collect = hearts.stream()
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

  private void sendAlarmToPostOwner(Member member, Post post) {
    if (isNotMyPost(member, post)) {
      Map<String, String> data = makeMessage(member, post);
      Note note = Note.makeNote(data);

      alarmService.saveAlarmPost(note, post.getMember(), post.getId(), member,
          Topic.HEART);

      if (canSendMessage(post)) {
        JsonData jsonData = new JsonData();
        String message = jsonData.make(data);
        kafkaProducer.sendMessage(Topic.HEART, message);
      }
    }
  }

  private Map<String, String> makeMessage(Member member, Post post) {
    Map<String, String> data = new HashMap<>();
    data.put("subject", "DAYO");
    data.put("body", member.getNickname() + "님이 회원님의 게시글을 좋아해요.");
    data.put("content", "님이 회원님의 게시글을 좋아해요.");
    data.put("deviceToken", post.getMember().getDeviceToken());
    data.put("postId", post.getId().toString());
    data.put("topic", Topic.HEART.toString());
    return data;
  }

  private boolean canSendMessage(Post post) {
    return post.getMember().getDeviceToken() != null && post.getMember().getOnReceiveAlarm();
  }

  private boolean isNotMyPost(Member member, Post post) {
    return !post.getMember().getId().equals(member.getId());
  }

}
