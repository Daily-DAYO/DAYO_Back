package com.seoultech.dayo.domain.hashtag.service;

import com.seoultech.dayo.domain.hashtag.Hashtag;
import com.seoultech.dayo.domain.hashtag.controller.dto.request.CreateHashtagRequest;
import com.seoultech.dayo.domain.hashtag.controller.dto.response.CreateHashtagResponse;
import com.seoultech.dayo.domain.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

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
