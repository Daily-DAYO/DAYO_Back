package com.seoultech.dayo.postHashtag.repository;


import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.postHashtag.PostHashtag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, PostHashtag.Key> {

  List<PostHashtag> findPostHashtagsByHashtag(Hashtag hashtag);

  void deleteAllByPost(Post post);

  @Query("select ph from PostHashtag ph join Post p on ph.post = p where ph.hashtag = :hashtag order by p.createdDate asc")
  List<PostHashtag> findPostHashtagsByHashtagOrderByAsc(@Param("hashtag") Hashtag hashtag);

  @Query("select ph from PostHashtag ph join Post p on ph.post = p where ph.hashtag = :hashtag order by p.createdDate desc")
  List<PostHashtag> findPostHashtagsByHashtagOrderByDesc(@Param("hashtag") Hashtag hashtag);

}
