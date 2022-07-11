package com.seoultech.dayo.heart.repository;

import com.seoultech.dayo.heart.Heart;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Post;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HeartRepository extends JpaRepository<Heart, Heart.Key> {

  List<Heart> findAllByMember(Member member);

  boolean existsHeartByKey(Heart.Key key);

  void deleteAllByMember(Member member);

  Optional<Heart> findHeartByMemberAndPost(Member member, Post post);

}
