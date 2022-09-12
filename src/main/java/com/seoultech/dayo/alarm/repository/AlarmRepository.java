package com.seoultech.dayo.alarm.repository;

import com.seoultech.dayo.alarm.Alarm;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

  List<Alarm> findAllByMember(Member member);

  void deleteAllByMember(Member member);

  void deleteAllBySender(Member sender);

  void deleteAlarmByPost(Post post);

}
