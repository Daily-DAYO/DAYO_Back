package com.seoultech.dayo.search.controller.dto.response;

import com.seoultech.dayo.search.controller.dto.SearchDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SearchResultResponse {

  private List<SearchDto> data;

  private int count;

  public static SearchResultResponse from(List<SearchDto> collect) {
    return new SearchResultResponse(collect, collect.size());
  }

}
