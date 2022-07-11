package com.seoultech.dayo.postHashtag.repository;


import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.postHashtag.PostHashtag;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostHashtagRepository extends JpaRepository<PostHashtag, PostHashtag.Key> {

  List<PostHashtag> findPostHashtagsByHashtag(Hashtag hashtag);

  void deleteAllByPost(Post post);

}
