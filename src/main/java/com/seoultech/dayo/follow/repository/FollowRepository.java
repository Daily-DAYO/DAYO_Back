package com.seoultech.dayo.follow.repository;

import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Follow.Key> {

    List<Follow> findFollowsByMember(Member member);

    List<Follow> findFollowsByFollower(Member follower);

    Optional<Follow> findFollowByMemberAndFollower(Member member, Member follower);

}
