package com.seoultech.dayo.search.controller;

import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.search.controller.dto.response.SearchHistoryResponse;
import com.seoultech.dayo.search.controller.dto.response.SearchResultResponse;
import com.seoultech.dayo.search.service.SearchService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

  private final SearchService searchService;
  private final MemberService memberService;

  @GetMapping
  public ResponseEntity<SearchResultResponse> search(@RequestParam String tag,
      HttpServletRequest servletRequest) {
    String memberId = servletRequest.getAttribute("memberId").toString();
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(searchService.search(member, tag));
  }

  @GetMapping("/history")
  public ResponseEntity<SearchHistoryResponse> searchHistory(HttpServletRequest servletRequest) {
    String memberId = servletRequest.getAttribute("memberId").toString();
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(searchService.searchHistory(member));
  }

  @PostMapping("/delete/history/{searchHistoryId}")
  public ResponseEntity<Void> deleteHistory(HttpServletRequest servletRequest,
      @PathVariable Long searchHistoryId) {
    String memberId = servletRequest.getAttribute("memberId").toString();
    Member member = memberService.findMemberById(memberId);

    searchService.deleteSearchHistory(searchHistoryId);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
