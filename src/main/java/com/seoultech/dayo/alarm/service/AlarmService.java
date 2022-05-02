package com.seoultech.dayo.alarm.service;

import com.seoultech.dayo.alarm.Alarm;
import com.seoultech.dayo.alarm.controller.dto.AlarmDto;
import com.seoultech.dayo.alarm.controller.dto.response.ListAllAlarmResponse;
import com.seoultech.dayo.alarm.repository.AlarmRepository;
import com.seoultech.dayo.config.fcm.Note;
import com.seoultech.dayo.member.Member;
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

  public void save(Note note, Member member, Long postId, String nickname) {
    alarmRepository.save(note.toEntity(member, postId, nickname));
  }

  public ListAllAlarmResponse listAll(Member member) {
    List<Alarm> alarmList = alarmRepository.findAllByMember(member);
    List<AlarmDto> collect = alarmList.stream()
        .map(AlarmDto::from)
        .collect(Collectors.toList());

    return ListAllAlarmResponse.from(collect);
  }

}
