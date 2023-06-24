package com.seoultech.dayo.search.controller.dto.response;

import com.seoultech.dayo.search.controller.dto.SearchDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchResultResponse {

  private List<SearchDto> data;

  private boolean last;

  private int count;

  private long allCount;

  public static SearchResultResponse from(List<SearchDto> collect, boolean last, long allCount) {
    return new SearchResultResponse(collect, last, collect.size(), allCount);
  }

}
