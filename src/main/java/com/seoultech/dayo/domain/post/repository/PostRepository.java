package com.seoultech.dayo.domain.post.repository;

import com.seoultech.dayo.domain.post.Category;
import com.seoultech.dayo.domain.post.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("select p from Post p join fetch p.member where p.category = :category")
    List<Post> findAllByCategoryUsingJoin(Category category);

    @Query("select p from Post p join fetch p.member where not p.privacy = 'ONLY_ME'")
    List<Post> findAllByUsingJoin();

}
