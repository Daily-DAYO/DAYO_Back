package com.seoultech.dayo.domain.hashtag.service;

import com.seoultech.dayo.domain.hashtag.Hashtag;
import com.seoultech.dayo.domain.hashtag.controller.dto.request.CreateHashtagRequest;
import com.seoultech.dayo.domain.hashtag.controller.dto.response.CreateHashtagResponse;
import com.seoultech.dayo.domain.hashtag.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class HashtagService {

    private final HashtagRepository hashtagRepository;

    public List<Hashtag> createHashtag(List<String> hashtags) {

        List<Hashtag> collect = hashtags.stream()
                .map(Hashtag::new)
                .collect(toList());

        return hashtagRepository.saveAll(collect);
    }

}
