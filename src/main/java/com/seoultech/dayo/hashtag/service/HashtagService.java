package com.seoultech.dayo.hashtag.service;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.repository.HashtagRepository;
//import com.seoultech.dayo.hashtag.repository.HashtagSearchRepository;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashtagService {

  private final HashtagRepository hashtagRepository;

  public List<Hashtag> createHashtag(List<String> tags) {

    List<Hashtag> hashtags = hashtagRepository.findByTags(tags);

    Set<String> collect = hashtags.stream()
        .map(Hashtag::getTag)
        .collect(toSet());

    List<Hashtag> notExists = tags.stream()
        .filter(tag -> !collect.contains(tag))
        .map(Hashtag::new)
        .collect(toList());

    if (notExists.size() > 0) {
      hashtagRepository.saveAll(notExists);
      hashtags.addAll(notExists);
    }

    return hashtags;
  }

  public Optional<Hashtag> findHashtag(String tag) {
    return hashtagRepository.findByTag(tag);
  }

}
