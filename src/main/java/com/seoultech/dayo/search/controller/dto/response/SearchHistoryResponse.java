package com.seoultech.dayo.search.controller.dto.response;

import com.seoultech.dayo.search.controller.dto.SearchHistoryDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SearchHistoryResponse {

  private List<SearchHistoryDto> data;

  private int count;

  public static SearchHistoryResponse from(List<SearchHistoryDto> collect) {
    return new SearchHistoryResponse(collect, collect.size());
  }

}
