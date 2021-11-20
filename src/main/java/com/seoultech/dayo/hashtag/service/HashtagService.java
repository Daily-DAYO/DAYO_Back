package com.seoultech.dayo.hashtag.service;

import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

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

        return collect;
    }

}
