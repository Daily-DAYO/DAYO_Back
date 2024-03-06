package com.seoultech.dayo.search.controller;

import com.seoultech.dayo.config.login.LoginUser;
import com.seoultech.dayo.member.Member;
import com.seoultech.dayo.member.service.MemberService;
import com.seoultech.dayo.search.controller.dto.response.SearchHistoryResponse;
import com.seoultech.dayo.search.controller.dto.response.SearchMemberResponse;
import com.seoultech.dayo.search.controller.dto.response.SearchResultResponse;
import com.seoultech.dayo.search.service.SearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {

  private final SearchService searchService;
  private final MemberService memberService;

  @Tag(name = "Search")
  @Operation(summary = "해시태그 게시글 검색 조회(페이징)")
  @ApiResponses(
      @ApiResponse(responseCode = "200", description = "해시태그 게시글 검색 조회 성공", content = @Content(schema = @Schema(implementation = SearchResultResponse.class))))
  @GetMapping
  public ResponseEntity<SearchResultResponse> search(@RequestParam String tag,
      @ApiIgnore @LoginUser String memberId,
      @RequestParam(value = "end") String end) {
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(searchService.search(member, tag, Long.valueOf(end)));
  }

  @Tag(name = "Search")
  @Operation(summary = "검색 내역 조회")
  @ApiResponses(
      @ApiResponse(responseCode = "200", description = "검색 내역 조회 성공", content = @Content(schema = @Schema(implementation = SearchHistoryResponse.class))))
  @GetMapping("/history")
  public ResponseEntity<SearchHistoryResponse> searchHistory(
      @ApiIgnore @LoginUser String memberId) {
    Member member = memberService.findMemberById(memberId);

    return ResponseEntity.ok()
        .body(searchService.searchHistory(member));
  }

  @Tag(name = "Search")
  @Operation(summary = "사용자 검색 조회(페이징)")
  @ApiResponses(
      @ApiResponse(responseCode = "200", description = "사용자 검색 조회 성공", content = @Content(schema = @Schema(implementation = SearchMemberResponse.class))))
  @GetMapping("/member")
  public ResponseEntity<SearchMemberResponse> searchMember(@RequestParam String nickname,
      @ApiIgnore @LoginUser String memberId,
      @RequestParam(value = "end") String end) {

    Member me = memberService.findMemberById(memberId);
    List<Member> findMembers = memberService.findMemberListByNicknameLike(nickname);
    return ResponseEntity.ok()
        .body(searchService.searchMember(me, findMembers, nickname, Long.valueOf(end)));

  }

  @PostMapping("/delete/history/{searchHistoryId}")
  public ResponseEntity<Void> deleteHistory(@ApiIgnore @LoginUser String memberId,
      @PathVariable Long searchHistoryId) {
    Member member = memberService.findMemberById(memberId);

    searchService.deleteSearchHistory(searchHistoryId);

    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
  }

}
