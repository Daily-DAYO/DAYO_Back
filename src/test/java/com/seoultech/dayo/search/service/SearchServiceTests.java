package com.seoultech.dayo.search.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.service.HashtagService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.post.Category;
import com.seoultech.dayo.post.Post;
import com.seoultech.dayo.postHashtag.PostHashtag;
import com.seoultech.dayo.postHashtag.service.PostHashtagService;
import com.seoultech.dayo.search.controller.dto.response.SearchHistoryResponse;
import com.seoultech.dayo.search.controller.dto.response.SearchResultResponse;
import com.seoultech.dayo.search.repository.SearchRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
class SearchServiceTests {

  @Mock
  HashtagService hashtagService;

  @Mock
  PostHashtagService postHashtagService;


  @InjectMocks
  SearchService searchService;

  Member member;
  Hashtag hashtag;
  PostHashtag postHashtag;
  Post post;

  List<PostHashtag> postHashtagList = new ArrayList<>();

  @BeforeEach
  public void init() {
    member = new Member("조재영", "jdyj@naver.com");
    hashtag = new Hashtag("테스트");
    post = new Post(member, "테스트1", "testimage", Category.SCHEDULER, null);
    postHashtag = new PostHashtag(post, hashtag);
  }

  @Test
  @DisplayName("검색 - 하나의 검색 결과가 있을 때")
  void searchTest() {
    postHashtagList.add(postHashtag);
    given(hashtagService.findHashtag(any())).willReturn(Optional.of(hashtag));
    given(postHashtagService.findPostHashtags(hashtag)).willReturn(postHashtagList);

    SearchResultResponse response = searchService.search(member, "테스트");

    assertThat(response.getCount()).isEqualTo(1);
  }

  @Test
  @DisplayName("검색 - 검색 결과가 없을 때")
  void searchTest2() {
    given(hashtagService.findHashtag(any())).willReturn(Optional.empty());
    given(postHashtagService.findPostHashtags(any())).willReturn(postHashtagList);

    SearchResultResponse response = searchService.search(member, "테스트");

    assertThat(response.getCount()).isEqualTo(0);
  }

}