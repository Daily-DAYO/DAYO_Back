package com.seoultech.dayo.alarm.service;

import com.seoultech.dayo.alarm.Alarm;
import com.seoultech.dayo.alarm.Topic;
import com.seoultech.dayo.alarm.controller.dto.AlarmDto;
import com.seoultech.dayo.alarm.controller.dto.response.ListAllAlarmResponse;
import com.seoultech.dayo.alarm.repository.AlarmRepository;
import com.seoultech.dayo.config.fcm.Note;
import com.seoultech.dayo.exception.NotExistAlarmException;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmService {

  private final AlarmRepository alarmRepository;

  public void saveAlarmPost(Note note, Member member, Post post, Member sender,
      Topic category) {
    alarmRepository.save(note.toEntityWithPostId(member, post, sender, category));
  }

  public void saveAlarmFollow(Note note, Member member, Member sender, Topic category) {
    alarmRepository.save(note.toEntityWithoutPostId(member, sender, category));
  }

  public ListAllAlarmResponse listAll(Member member, Long end) {
    List<Alarm> alarmList = alarmRepository.findAllByMember(member);

    boolean last = false;
    int size = alarmList.size();
    if (size <= end + 10) {
      last = true;
    }

    List<AlarmDto> collect = alarmList.stream()
        .map(AlarmDto::from)
        .sorted((a1, a2) -> a2.getCreatedTime().compareTo(a1.getCreatedTime()))
        .skip(end)
        .limit(10)
        .collect(Collectors.toList());

    return ListAllAlarmResponse.from(collect, last);
  }

  public void isCheckAlarm(Long alarmId) {

    Alarm alarm = alarmRepository.findById(alarmId)
        .orElseThrow(NotExistAlarmException::new);

    if (alarm.getIsCheck()) {
      return;
    }

    alarm.setCheck(true);
  }

  public void deleteByPost(Post post) {
    alarmRepository.deleteAlarmByPost(post);
  }

  public void deleteAllByMember(Member member) {
    alarmRepository.deleteAllByMember(member);
    alarmRepository.deleteAllBySender(member);
  }

  public void deleteHeart(Member sender, Post post) {
    alarmRepository.deleteAlarmBySenderAndPostAndCategory(sender, post, Topic.HEART);
  }

  public void deleteComment(Member sender, Post post) {
    alarmRepository.deleteAlarmBySenderAndPostAndCategory(sender, post, Topic.COMMENT);
  }

  public void deleteFollow(Member member, Member sender) {
    alarmRepository.deleteAlarmByMemberAndSenderAndCategory(member, sender, Topic.FOLLOW);
  }

}
