package com.seoultech.dayo.post.repository;

import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

  @Query("select p from Post p join fetch p.member where p.category = :category and p.privacy = 'ALL' order by p.createdDate desc")
  List<Post> findAllByCategoryUsingJoinOrderByCreateDate(Category category);

  @Query("select p from Post p join fetch p.member where p.privacy = 'ALL' order by p.createdDate desc")
  List<Post> findAllByUsingJoinMemberOrderByCreateDate();

  @Query("select p from Post p join fetch p.comments where p.id = :id")
  Optional<Post> findByIdWithComment(@Param("id") Long id);
}
