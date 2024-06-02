package com.seoultech.dayo.search.controller.dto.response;

import com.seoultech.dayo.search.controller.dto.SearchMemberInCommentDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchMemberInCommentResponse {

  private List<SearchMemberInCommentDto> data;

  private boolean last;

  private int count;

  private long allCount;

  public static SearchMemberInCommentResponse from(List<SearchMemberInCommentDto> collect,
      boolean last,
      long allCount) {
    return new SearchMemberInCommentResponse(collect, last, collect.size(), allCount);
  }
}
