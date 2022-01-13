package com.seoultech.dayo.hashtag.service;

import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.repository.HashtagRepository;
import com.seoultech.dayo.hashtag.repository.HashtagSearchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class HashtagService {

    private final HashtagRepository hashtagRepository;
    private final HashtagSearchRepository hashtagSearchRepository;

    public List<Hashtag> createHashtag(List<String> tags) {

        List<Hashtag> hashtags = hashtagRepository.findByTags(tags);
        List<Hashtag> collect = new ArrayList<>();
        List<Hashtag> notExists = new ArrayList<>();

        for (String tag : tags) {
            boolean notExist = true;
            for (Hashtag hashtag : hashtags) {
                if(hashtag.getTag().equals(tag)) {
                    collect.add(hashtag);
                    notExist = false;
                    break;
                }
            }
            if(notExist) {
                Hashtag hashtag = new Hashtag(tag);
                collect.add(hashtag);
                notExists.add(hashtag);
            }
        }

        hashtagRepository.saveAll(notExists);
        hashtagSearchRepository.saveAll(notExists);

        return collect;
    }

    public void findHashtag(String tag) {

        List<Hashtag> hashtags = hashtagSearchRepository.findHashtagsByTag(tag);

        for(Hashtag hashtag : hashtags) {
            System.out.println(hashtag.getTag());
        }
    }

}
