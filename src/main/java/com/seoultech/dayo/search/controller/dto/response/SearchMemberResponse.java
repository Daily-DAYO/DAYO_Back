package com.seoultech.dayo.search.controller.dto.response;

import com.seoultech.dayo.search.controller.dto.SearchDto;
import com.seoultech.dayo.search.controller.dto.SearchMemberDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchMemberResponse {

  private List<SearchDto> data;

  private boolean last;

  private int count;

  private long allCount;

  public static SearchMemberResponse from(List<SearchMemberDto> collect, boolean last,
      long allCount) {
    return new SearchMemberResponse(collect, last, collect.size(), allCount);
  }

}
