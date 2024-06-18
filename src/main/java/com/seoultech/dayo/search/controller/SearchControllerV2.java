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
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Tag(name = "Search", description = "검색 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2/search")
public class SearchControllerV2 {

  private final SearchService searchService;
  private final MemberService memberService;

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

}
