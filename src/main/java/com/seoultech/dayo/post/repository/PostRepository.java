package com.seoultech.dayo.post.repository;

import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostRepository extends JpaRepository<Post, Long> {

  @Query("select p from Post p join fetch p.member where p.category = :category and p.privacy = 'ALL' order by p.createdDate desc")
  List<Post> findAllByCategoryUsingJoinOrderByCreateDate(@Param("category") Category category);

  @Query("select p from Post p join fetch p.member where p.privacy = 'ALL' order by p.createdDate desc")
  List<Post> findAllByUsingJoinMemberOrderByCreateDate();

  @Query("select p from Post p join fetch p.comments where p.id = :id")
  Optional<Post> findByIdWithComment(@Param("id") Long id);

  @Query("select p from Post p join fetch p.member where p.category = :category and p.privacy = 'ALL'")
  List<Post> findAllByCategoryUsingJoinMember(@Param("category") Category category);

  @Query("select p from Post p join fetch p.member")
  List<Post> findAllUsingJoinMember();

  void deleteAllByMember(Member member);

}
