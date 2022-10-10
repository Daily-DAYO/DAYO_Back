package com.seoultech.dayo.alarm.repository;

import com.seoultech.dayo.alarm.Alarm;
import com.seoultech.dayo.alarm.Topic;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AlarmRepository extends JpaRepository<Alarm, Long> {

  List<Alarm> findAllByMember(Member member);

  boolean existsBySenderAndPostAndCategory(Member sender, Post post, Topic category);

  void deleteAllByMember(Member member);

  void deleteAllBySender(Member sender);

  void deleteAlarmByPost(Post post);

  void deleteAlarmBySenderAndPostAndCategory(Member sender, Post post, Topic category);

  void deleteAlarmByMemberAndSenderAndCategory(Member member, Member sender, Topic category);

}
