package com.seoultech.dayo.follow.repository;

import com.seoultech.dayo.follow.Follow;
import com.seoultech.dayo.member.Member;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Follow.Key> {

  List<Follow> findFollowsByMember(Member member);

  List<Follow> findFollowsByFollower(Member follower);

  Optional<Follow> findFollowByMemberAndFollower(Member member, Member follower);

  void deleteAllByMember(Member member);

  void deleteAllByFollower(Member follower);

  void deleteFollowByMemberAndFollower(Member member, Member follower);

  @Query("select f from Follow f join Member m on f.follower.id = m.id where f.member = :member and m.nickname like concat('%', :nickname, '%')")
  List<Follow> findFollowsByMemberLikeNickname(@Param("member") Member tags,
      @Param("nickname") String nickname);

}
