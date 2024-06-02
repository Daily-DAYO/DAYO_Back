package com.seoultech.dayo.postHashtag.service;

import static java.util.stream.Collectors.toList;

import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.postHashtag.PostHashtag;
import com.seoultech.dayo.postHashtag.repository.PostHashtagRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

  public List<PostHashtag> findPostHashtagsByPostCreatedAsc(Hashtag hashtag) {
    return postHashtagRepository.findPostHashtagsByHashtagOrderByAsc(hashtag);
  }

  public List<PostHashtag> findPostHashtagsByPostCreatedDesc(Hashtag hashtag) {
    return postHashtagRepository.findPostHashtagsByHashtagOrderByDesc(hashtag);
  }

  public void deletePostHashtag(Post post) {
    postHashtagRepository.deleteAllByPost(post);
  }


}
