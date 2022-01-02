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



}