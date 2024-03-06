package com.seoultech.dayo.search.service;

import static java.util.stream.Collectors.toList;

import com.seoultech.dayo.block.service.BlockService;
import com.seoultech.dayo.hashtag.Hashtag;
import com.seoultech.dayo.hashtag.service.HashtagService;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.postHashtag.PostHashtag;
import com.seoultech.dayo.postHashtag.service.PostHashtagService;
import com.seoultech.dayo.search.Search;
import com.seoultech.dayo.search.controller.dto.SearchDto;
import com.seoultech.dayo.search.controller.dto.SearchHistoryDto;
import com.seoultech.dayo.search.controller.dto.SearchMemberDto;
import com.seoultech.dayo.search.controller.dto.response.SearchHistoryResponse;
import com.seoultech.dayo.search.controller.dto.response.SearchMemberResponse;
import com.seoultech.dayo.search.controller.dto.response.SearchResultResponse;
import com.seoultech.dayo.search.repository.SearchRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
  private final BlockService blockService;

  public SearchResultResponse search(Member member, String tag, Long end) {
    Search search = new Search(member, tag);
    searchRepository.save(search);

    Optional<Hashtag> hashtag = hashtagService.findHashtag(tag);
    List<SearchDto> collect = new ArrayList<>();

    boolean last = false;
    long allCount = 0L;
    if (hashtag.isPresent()) {

      List<PostHashtag> postHashtags = postHashtagService.findPostHashtags(hashtag.get());
      allCount = postHashtags.size();
      if (postHashtags.size() <= end + 10) {
        last = true;
      }
      collect = postHashtags.stream()
          .skip(end)
          .limit(10)
          .map(postHashtag -> SearchDto.from(postHashtag.getPost()))
          .collect(toList());
    }

    return SearchResultResponse.from(collect, last, allCount);
  }

  public SearchHistoryResponse searchHistory(Member member) {
    List<Search> searches = searchRepository.findSearchesByMember(member);
    List<SearchHistoryDto> collect = searches.stream()
        .map(SearchHistoryDto::from)
        .collect(toList());

    return SearchHistoryResponse.from(collect);
  }

  public SearchMemberResponse searchMember(Member member, List<Member> searchMembers,
      String nickname, Long end) {
    Search search = new Search(member, nickname);
    searchRepository.save(search);

    Set<String> blockedMemberList = blockService.getBlockedMemberList(member);
    Set<String> blockingMemberList = blockService.getBlockingMemberList(member);

    List<Member> userList = new ArrayList<>();

    for (Member user : searchMembers) {
      if (!blockedMemberList.contains(user.getId()) && !blockingMemberList.contains(user.getId())) {
        userList.add(user);
      }
    }

    boolean last = false;
    long allCount = 0L;
    allCount = userList.size();
    if (userList.size() <= end + 10) {
      last = true;
    }
    List<SearchMemberDto> collect = userList.stream()
        .skip(end)
        .limit(10)
        .map(SearchMemberDto::from)
        .collect(toList());

    return SearchMemberResponse.from(collect, last, allCount);

  }

  public void deleteSearchHistory(Long searchId) {
    searchRepository.deleteById(searchId);
  }

  public void deleteAllByMember(Member member) {
    searchRepository.deleteAllByMember(member);
  }
}
