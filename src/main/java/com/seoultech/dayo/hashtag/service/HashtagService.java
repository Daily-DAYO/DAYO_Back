package com.seoultech.dayo.hashtag.service;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.repository.HashtagRepository;
import com.seoultech.dayo.hashtag.repository.HashtagSearchRepository;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HashtagService {

  private final HashtagRepository hashtagRepository;
  private final HashtagSearchRepository hashtagSearchRepository;

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
      hashtagSearchRepository.saveAll(notExists);
      hashtags.addAll(notExists);
    }

    return hashtags;
  }

  public void findHashtag(String tag) {

    List<Hashtag> hashtags = hashtagSearchRepository.findHashtagsByTag(tag);

    for (Hashtag hashtag : hashtags) {
      log.info(hashtag.getTag());
    }
  }

}
