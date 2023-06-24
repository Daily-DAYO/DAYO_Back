package com.seoultech.dayo.search.service;

import static java.util.stream.Collectors.toList;

import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.service.HashtagService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.postHashtag.PostHashtag;
import com.seoultech.dayo.postHashtag.service.PostHashtagService;
import com.seoultech.dayo.search.Search;
import com.seoultech.dayo.search.controller.dto.SearchDto;
import com.seoultech.dayo.search.controller.dto.SearchHistoryDto;
import com.seoultech.dayo.search.controller.dto.response.SearchHistoryResponse;
import com.seoultech.dayo.search.controller.dto.response.SearchResultResponse;
import com.seoultech.dayo.search.repository.SearchRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class SearchService {

  private final SearchRepository searchRepository;
  private final HashtagService hashtagService;
  private final PostHashtagService postHashtagService;

  public SearchResultResponse search(Member member, String tag, Long end) {
    Search search = new Search(member, tag);
    searchRepository.save(search);

    Optional<Hashtag> hashtag = hashtagService.findHashtag(tag);
    List<SearchDto> collect = new ArrayList<>();

    boolean last = false;

    if (hashtag.isPresent()) {

      List<PostHashtag> postHashtags = postHashtagService.findPostHashtags(hashtag.get());
      if (postHashtags.size() <= end + 10) {
        last = true;
      }
      collect = postHashtags.stream()
          .skip(end)
          .limit(10)
          .map(postHashtag -> SearchDto.from(postHashtag.getPost()))
          .collect(toList());
    }
    long allCount = collect.size() + end;

    return SearchResultResponse.from(collect, last, allCount);
  }

  public SearchHistoryResponse searchHistory(Member member) {
    List<Search> searches = searchRepository.findSearchesByMember(member);
    List<SearchHistoryDto> collect = searches.stream()
        .map(SearchHistoryDto::from)
        .collect(toList());

    return SearchHistoryResponse.from(collect);
  }

  public void deleteSearchHistory(Long searchId) {
    searchRepository.deleteById(searchId);
  }

  public void deleteAllByMember(Member member) {
    searchRepository.deleteAllByMember(member);
  }
}
