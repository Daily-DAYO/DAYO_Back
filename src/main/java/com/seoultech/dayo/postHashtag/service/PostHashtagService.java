package com.seoultech.dayo.postHashtag.service;

import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.postHashtag.PostHashtag;
import com.seoultech.dayo.postHashtag.repository.PostHashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class PostHashtagService {

  private final PostHashtagRepository postHashtagRepository;

  public void createPostHashtag(Post post, List<Hashtag> hashtags) {
    postHashtagRepository.saveAll(hashtags.stream()
        .map(hashtag -> new PostHashtag(post, hashtag))
        .collect(toList()));
  }

  public List<PostHashtag> saveAll(List<PostHashtag> postHashtags) {
    return postHashtagRepository.saveAll(postHashtags);
  }

  public PostHashtag save(PostHashtag postHashtag) {
    return postHashtagRepository.save(postHashtag);
  }

  public List<PostHashtag> findPostHashtags(Hashtag hashtag) {
    return postHashtagRepository.findPostHashtagsByHashtag(hashtag);
  }


}
