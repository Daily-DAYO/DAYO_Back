package com.seoultech.dayo.hashtag.service;

import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.repository.HashtagRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class HashtagServiceTests {

    @Mock
    private HashtagRepository hashtagRepository;

    @InjectMocks
    private HashtagService hashtagService;

    @Test
    void 해시태그_생성() {

        //given
        Hashtag hashtag1 = new Hashtag(1L, "태그1");
        Hashtag hashtag2 = new Hashtag(2L, "태그2");

        List<Hashtag> hashtags = new ArrayList<>();
        hashtags.add(hashtag1);
        hashtags.add(hashtag2);

        given(hashtagRepository.findByTags(any())).willReturn(hashtags);




    }


}