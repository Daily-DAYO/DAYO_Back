package com.seoultech.dayo.alarm.repository;

import com.seoultech.dayo.alarm.service.AlarmRepository;
import com.seoultech.dayo.config.fcm.Note;
import com.seoultech.dayo.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmService {

  private final AlarmRepository alarmRepository;

  public void save(Note note, Member member) {
    alarmRepository.save(note.toEntity(member));
  }

}
